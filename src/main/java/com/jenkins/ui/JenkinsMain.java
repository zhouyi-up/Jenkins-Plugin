package com.jenkins.ui;

import com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetWrapper;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBImageIcon;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.model.JobListEntity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author corel
 */
public class JenkinsMain extends JPanel {

    JLabel jobText;
    JBList<JobListItemView> jbList;
    DefaultListModel<JobListItemView> defaultListModel;
    JenkinsClientAsync jenkinsClientAsync;

    public JenkinsMain(JenkinsClientAsync jenkinsClientAsync){
        this.jenkinsClientAsync = jenkinsClientAsync;

        setLayout(new BorderLayout());

        initBtnPanelView();
        initJobListView();
        initJobListData();
    }

    private void initJobListView(){
        defaultListModel = new DefaultListModel<>();
        jbList = new JBList<>();
        jbList.setModel(defaultListModel);
        jbList.setFixedCellHeight(30);
        jbList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Object selectedValue = jbList.getSelectedValue();
                jobText.setText(String.valueOf(selectedValue));
            }
        });
        jbList.setCellRenderer(new JenkinsListCellRenderer());

        JBScrollPane jbScrollPane =new JBScrollPane(jbList);

        add(jbScrollPane, BorderLayout.CENTER);
    }

    private void initBtnPanelView(){
        JPanel jPanel = new JPanel();
        jobText = new JLabel();
        jPanel.add(jobText);

        JButton testBtn = new JButton("TestBtn");
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListModel<JobListItemView> model = jbList.getModel();
                int size = model.getSize() + 1;
                String name = "ele-" + size;
                defaultListModel.addElement(new JobListItemView(name));
            }
        });
        jPanel.add(testBtn);

        JButton buildBtn = new JButton("Build");
        buildBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobListItemView selectedValue = jbList.getSelectedValue();
                jenkinsClientAsync.build(selectedValue.getJobName(), new DefaultCallback<String>() {
                    @Override
                    public void success(String data) {

                    }
                });
            }
        });
        jPanel.add(buildBtn);

        add(jPanel, BorderLayout.NORTH);
    }

    private void initJobListData(){
        jenkinsClientAsync.jobList(new DefaultCallback<JobListEntity>() {
            @Override
            public void success(JobListEntity data) {
                List<JobListEntity.JobsBean> jobs = data.getJobs();
                jobs.forEach(bean -> {
                    defaultListModel.addElement(new JobListItemView(bean.getName()));
                });
            }
        });
    }
}
