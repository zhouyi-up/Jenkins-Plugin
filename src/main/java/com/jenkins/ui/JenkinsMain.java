package com.jenkins.ui;

import com.google.common.collect.Maps;
import com.intellij.icons.AllIcons;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.treeStructure.Tree;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author corel
 */
public class JenkinsMain extends JPanel {

    JBLabel jobText;
    JenkinsClientAsync jenkinsClientAsync;
    JenkinsRootTreeNode rootNode;
    JenkinsBuildView jenkinsBuildView;

    private Map<String, JobEntity> JOB_MAP = Maps.newConcurrentMap();

    public JenkinsMain(JenkinsClientAsync jenkinsClientAsync){
        this.jenkinsClientAsync = jenkinsClientAsync;

        setLayout(new BorderLayout());

        rootNode = new JenkinsRootTreeNode();

        Tree jTree = new Tree(rootNode);

        jTree.setCellRenderer(new JenkinsTreeCellRenderer());

        jTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Object lastSelectedPathComponent = jTree.getLastSelectedPathComponent();
                if (lastSelectedPathComponent == null){
                    return;
                }
            }
        });

        jTree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
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
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

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
                    if (e.getClickCount() >= 2){
                        JobEntity jobBean = JOB_MAP.get(jobName);
                        if (jobBean == null){
                            return;
                        }

                        boolean isParam = true;

                        List<JobEntity.PropertyBean> property = jobBean.getProperty();
                        if (property.isEmpty()){
                            isParam = false;
                        }
                        List<JobEntity.PropertyBean> collect = property.stream()
                                .filter(item -> item.get_class().equals("hudson.model.ParametersDefinitionProperty"))
                                .collect(Collectors.toList());
                        if (collect.isEmpty()){
                            isParam = false;
                        }

                        if (!isParam){
                            jenkinsClientAsync.build(jobName, new DefaultCallback<String>() {
                                @Override
                                public void success(String data) {
                                    System.out.println("build success");
                                }
                            });
                            return;
                        }
                        jenkinsBuildView = null;
                        jenkinsBuildView = new JenkinsBuildView(jenkinsClientAsync, jobBean);
                    }
                }

                if (lastSelectedPathComponent instanceof JenkinsTreeNode){
                    JenkinsTreeNode jenkinsTreeNode = (JenkinsTreeNode) lastSelectedPathComponent;
                    String jobName = jenkinsTreeNode.getJobName();

                    jenkinsClientAsync.jobInfo(jobName, new DefaultCallback<JobEntity>() {
                        @Override
                        public void success(JobEntity data) {
                            JOB_MAP.put(data.getName(), data);
                        }
                    });
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
     * @param jobName
     */
    private void addNode(String jobName){
        JenkinsTreeNode jenkinsTreeNode = new JenkinsTreeNode(jobName);
        rootNode.add(jenkinsTreeNode);
    }

    private void initBtnPanelView(){
        JPanel jPanel = new JPanel();
        jobText = new JBLabel();
        jPanel.add(jobText);

        JButton testBtn = new JButton("TestBtn");
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("TestBtn");
                int size = rootNode.getChildCount() + 1;
                String name = "ele-" + size;
                addNode(name);
            }
        });
        jPanel.add(testBtn);

        add(jPanel, BorderLayout.NORTH);
    }

    private void initData(){
        jenkinsClientAsync.jobList(new DefaultCallback<JobListEntity>() {
            @Override
            public void success(JobListEntity data) {
                List<JobListEntity.JobsBean> jobs = data.getJobs();
                jobs.forEach(bean -> {
                    rootNode.add(new JenkinsTreeNode(bean.getName()));
                    JOB_MAP.put(bean.getName(), new JobEntity());
                });
            }
        });
    }

    private void initJobInfo(){
        JOB_MAP.entrySet().forEach(entry -> {
            String jobName = entry.getKey();
            jenkinsClientAsync.jobInfo(jobName, new DefaultCallback<JobEntity>() {
                @Override
                public void success(JobEntity data) {
                    entry.setValue(data);
                }
            });
        });
    }
}
