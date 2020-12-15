package com.jenkins.ui;

import com.intellij.icons.AllIcons;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.treeStructure.Tree;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.model.JobListEntity;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * @author corel
 */
public class JenkinsMain extends JPanel {

    JBLabel jobText;
    JBTable jbTable;
    DefaultTableModel defaultTableModel;
    JenkinsClientAsync jenkinsClientAsync;
    DefaultMutableTreeNode rootNode;

    public JenkinsMain(JenkinsClientAsync jenkinsClientAsync){
        this.jenkinsClientAsync = jenkinsClientAsync;

        setLayout(new BorderLayout());


        rootNode = new DefaultMutableTreeNode("Jenkins");

        Tree jTree = new Tree(rootNode);
        DefaultTreeCellRenderer defaultTreeCellRenderer = new DefaultTreeCellRenderer();
        defaultTreeCellRenderer.setLeafIcon(AllIcons.Logo_welcomeScreen);
        defaultTreeCellRenderer.setClosedIcon(AllIcons.Nodes.AbstractClass);
        defaultTreeCellRenderer.setOpenIcon(AllIcons.Nodes.Aspect);
        jTree.setCellRenderer(defaultTreeCellRenderer);

        jTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                JenkinsTreeNode jenkinsTreeNode = (JenkinsTreeNode) jTree.getLastSelectedPathComponent();
                if (jenkinsTreeNode == null){
                    return;
                }
                System.out.println(jenkinsTreeNode.getJobName());

            }
        });

        JBScrollPane jbScrollPane = new JBScrollPane(jTree);
        add(jbScrollPane, BorderLayout.CENTER);



//        initJobTableView();

//        initBtnPanelView();
//        initJobListView();
        initData();
    }

    private void initJobTableView(){

        Vector<String> title = new Vector<>();
        title.add("任务");
        title.add("操作");

        Vector<Vector> data = new Vector<>();

        Vector<String> data1 = new Vector<>();
        data1.add("Test");
        data1.add("----");

        data.add(data1);

        defaultTableModel = new DefaultTableModel(data, title);



        jbTable = new JBTable(defaultTableModel);

        JBScrollPane jbScrollPane = new JBScrollPane(jbTable);

        add(jbScrollPane, BorderLayout.CENTER);
    }

    private void initBtnPanelView(){
        JPanel jPanel = new JPanel();
        jobText = new JBLabel();
        jPanel.add(jobText);

        JButton testBtn = new JButton("TestBtn");
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = jbTable.getModel();
                int size = model.getRowCount() + 1;
                String name = "ele-" + size;
                defaultTableModel.addRow(new String[]{name});
            }
        });
        jPanel.add(testBtn);

        JButton buildBtn = new JButton("Build");
        buildBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jbTable.getSelectedRow();
                Object valueAt = defaultTableModel.getValueAt(selectedRow, 0);
                jenkinsClientAsync.build(valueAt.toString(), new DefaultCallback<String>() {
                    @Override
                    public void success(String data) {

                    }
                });
            }
        });
        jPanel.add(buildBtn);

        add(jPanel, BorderLayout.NORTH);
    }

    private void initData(){
        jenkinsClientAsync.jobList(new DefaultCallback<JobListEntity>() {
            @Override
            public void success(JobListEntity data) {
                List<JobListEntity.JobsBean> jobs = data.getJobs();
                jobs.forEach(bean -> {
                    rootNode.add(new JenkinsTreeNode(bean.getName()));
                });
            }
        });
    }
}
