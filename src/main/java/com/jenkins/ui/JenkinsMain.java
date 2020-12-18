package com.jenkins.ui;

import com.google.common.collect.Maps;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.BalloonImpl;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.roots.ToolbarPanel;
import com.intellij.ui.treeStructure.Tree;
import com.jenkins.client.BuildParam;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.compent.JenkinsComponent;
import com.jenkins.compent.JenkinsIcons;
import com.jenkins.compent.JenkinsNotificationComponent;
import com.jenkins.compent.JenkinsSettingDataComponent;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author corel
 */
public class JenkinsMain extends SimpleToolWindowPanel {

    public static final int CLICK_COUNT = 2;

    private JenkinsComponent jenkinsComponent;

    JBLabel jobText;
    JenkinsRootTreeNode rootNode;
    JenkinsBuildView jenkinsBuildView;
    Tree jTree;
    DefaultTreeModel treeModel;

    private final Map<String, JobEntity> jobMap = Maps.newConcurrentMap();

    public JenkinsMain(Project project){
        super(true);
        this.jenkinsComponent = JenkinsComponent.getInstance(project);

        setLayout(new BorderLayout());

        rootNode = new JenkinsRootTreeNode();

        jTree = new Tree(rootNode);
        treeModel = (DefaultTreeModel) jTree.getModel();

        jTree.setCellRenderer(new JenkinsTreeCellRenderer());

        jTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(e -> {
            Object lastSelectedPathComponent = jTree.getLastSelectedPathComponent();
            if (lastSelectedPathComponent == null){
                System.out.println("GG");
            }
        });

        jTree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) {
                TreePath path = event.getPath();
                if (path == null){
                    return;
                }
                Object lastPathComponent = path.getLastPathComponent();
                if (lastPathComponent == null){
                    return;
                }
                if (lastPathComponent instanceof JenkinsRootTreeNode){
                    initJobInfo();
                }
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) {

            }
        });

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component deepestRendererComponentAt = jTree.getDeepestRendererComponentAt(e.getX(), e.getY());
                if (deepestRendererComponentAt == null){
                    return;
                }

                Object lastSelectedPathComponent = jTree.getLastSelectedPathComponent();
                if (lastSelectedPathComponent == null){
                    return;
                }

                if (lastSelectedPathComponent instanceof JenkinsBuildTreeNode){
                    JenkinsBuildTreeNode selectNode = (JenkinsBuildTreeNode) lastSelectedPathComponent;
                    String jobName = selectNode.getJobName();
                    if (e.getClickCount() >= CLICK_COUNT){
                        JobEntity jobBean = jobMap.get(jobName);
                        if (jobBean == null){
                            return;
                        }

                        boolean isParam = true;

                        List<JobEntity.PropertyBean> property = jobBean.getProperty();
                        if (property.isEmpty()){
                            isParam = false;
                        }
                        List<JobEntity.PropertyBean> collect = property.stream()
                                .filter(item -> "hudson.model.ParametersDefinitionProperty".equals(item.get_class()))
                                .collect(Collectors.toList());
                        if (collect.isEmpty()){
                            isParam = false;
                        }

                        if (!isParam){
                            jenkinsComponent.build(jobName, data -> {}, ()->{});
                            return;
                        }
                        jenkinsBuildView = new JenkinsBuildView(jobBean);
                        boolean b = jenkinsBuildView.showAndGet();
                        if (b){
                            Map<Object, Object> buildParamMap = jenkinsBuildView.getBuildParamMap();

                            BuildParam buildParam = new BuildParam();
                            buildParam.setJobName(jobBean.getName());
                            buildParam.addParamForObject(buildParamMap);

                            jenkinsComponent.build(buildParam,data -> {}, ()->{});
                        }
                    }
                }

                if (lastSelectedPathComponent instanceof JenkinsTreeNode){
                    JenkinsTreeNode jenkinsTreeNode = (JenkinsTreeNode) lastSelectedPathComponent;
                    String jobName = jenkinsTreeNode.getJobName();

                    jenkinsComponent.jobInfo(jobName, data -> {
                        jobMap.put(data.getName(), data);
                    }, ()->{});
                }
            }
        };

        jTree.addMouseListener(mouseAdapter);

        JBScrollPane jbScrollPane = new JBScrollPane(jTree);
        add(jbScrollPane, BorderLayout.CENTER);

        initBtnPanelView();
        initData();

    }

    /**
     * 添加一个job任务节点
     * @param jobName 任务名称
     */
    private void addNode(String jobName){
        JenkinsTreeNode jenkinsTreeNode = new JenkinsTreeNode(jobName);
        jenkinsTreeNode.setAllowsChildren(true);
        treeModel.insertNodeInto(jenkinsTreeNode,rootNode, rootNode.getChildCount());
//        jTree.scrollPathToVisible(new TreePath(jenkinsTreeNode.getPath()));
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
                jTree.updateUI();
            });
        }, () -> {});

    }

    private void initData(){
        jenkinsComponent.jobList(data -> {
            List<JobListEntity.JobsBean> jobs = data.getJobs();
            jobs.forEach(bean -> {
                rootNode.add(new JenkinsTreeNode(bean.getName()));
                jobMap.put(bean.getName(), new JobEntity());
            });
        }, () -> {});
    }

    private void initJobInfo(){
        jobMap.entrySet().forEach(entry -> {
            String jobName = entry.getKey();
            jenkinsComponent.jobInfo(jobName, entry::setValue, () -> {});
        });
    }
}
