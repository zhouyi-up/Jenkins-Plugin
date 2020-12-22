package com.jenkins.ui.tooltab;

import com.google.common.collect.Maps;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import com.jenkins.compent.JenkinsComponent;
import com.jenkins.compent.JenkinsIcons;
import com.jenkins.compent.JenkinsNotificationComponent;
import com.jenkins.compent.JenkinsSettingDataComponent;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @author corel
 */
public class JenkinsMain extends SimpleToolWindowPanel {

    private final JenkinsComponent jenkinsComponent;
    private final JenkinsSettingDataComponent dataComponent;

    JenkinsRootTreeNode rootNode;
    Tree jTree;
    DefaultTreeModel treeModel;
    Project project;

    private final Map<String, JobEntity> jobMap = Maps.newConcurrentMap();

    public JenkinsMain(Project project){
        super(true);
        this.project = project;
        this.jenkinsComponent = JenkinsComponent.getInstance(project);
        this.dataComponent = JenkinsSettingDataComponent.getInstance();

        setLayout(new BorderLayout());

        initMainPanel();
        initBtnPanelView();

        if (!dataComponent.getInited()){
            JenkinsNotificationComponent.notifySuccess(project, "Jenkins Plugin", "You need to configure for jenkins.");
        }
    }

    private void initMainPanel() {
        rootNode = new JenkinsRootTreeNode();

        jTree = new Tree(rootNode);
        treeModel = (DefaultTreeModel) jTree.getModel();
        jTree.setCellRenderer(new JenkinsTreeCellRenderer());
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeWillExpandListener(new JenkinsMainTreeWillExpandListener(jobMap, project, jenkinsComponent));
        jTree.addMouseListener(new JenkinsMainMouseAdapter(project, jobMap, jenkinsComponent));

        JBScrollPane jbScrollPane = new JBScrollPane(jTree);
        add(jbScrollPane, BorderLayout.CENTER);
    }

    /**
     * 添加一个job任务节点
     * @param jobName 任务名称
     */
    private void addNode(String jobName){
        JenkinsTreeNode jenkinsTreeNode = new JenkinsTreeNode(jobName);
        jenkinsTreeNode.setAllowsChildren(true);
        treeModel.insertNodeInto(jenkinsTreeNode,rootNode, rootNode.getChildCount());
    }

    /**
     * 初始化按钮
     */
    private void initBtnPanelView(){
        DefaultActionGroup defaultActionGroup = new DefaultActionGroup();
        AnAction refresh = new AnAction(JenkinsIcons.REFRESH) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                refreshTree();
            }
        };
        defaultActionGroup.add(refresh);

        AnAction closeAll = new AnAction(JenkinsIcons.COLLAPSEALL) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                int childCount = rootNode.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    jTree.collapseRow(i);
                }
            }
        };
        defaultActionGroup.add(closeAll);

        JComponent actionToolbar = ActionManager.getInstance()
                .createActionToolbar("Jenkins", defaultActionGroup, true).getComponent();
        setToolbar(actionToolbar);
    }

    private void refreshTree(){
        rootNode.removeAllChildren();
        jenkinsComponent.jobList(data -> {
            List<JobListEntity.JobsBean> jobs = data.getJobs();
            jobs.forEach(bean -> {
                addNode(bean.getName());
                jobMap.put(bean.getName(), new JobEntity());
            });
            jTree.updateUI();
            JenkinsNotificationComponent.notifySuccess(project, "Refresh Successful!");
        }, () -> {});

    }
}
