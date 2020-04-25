package com.jenkins.windows;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.jenkins.JenkinsClientUtils;
import com.jenkins.JenkinsPropertiesComponent;
import com.jenkins.JenkinsResponse;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class BuildWindow {
    private JPanel panel;
    private JButton buildBtn;
    private JPanel buildBtnPanel;
    private JPanel buildParamPanel;
    private JLabel jenkinsDisplayText;
    private JButton restParamBtn;
    private JTable buildParamTable;
    private JComboBox<String> jobListBox;
    private JLabel selectJobLabel;
    private JButton refreshBtn;
    private JLabel titleBtn;

    private String selectItem;

    public BuildWindow() {

        init();

        buildBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                jenkinsDisplayText.setText("Waiting...");
                ParamTableModel<String> paramTableModel = (ParamTableModel<String>) buildParamTable.getModel();
                int columnCount = paramTableModel.getColumnCount();
                int rowCount = paramTableModel.getRowCount();

                String[][] dataArr = new String[rowCount][columnCount];
                for (int i = 0; i < columnCount; i++) {
                    for (int j = 0; j < rowCount; j++) {
                        String valueAt = String.valueOf(paramTableModel.getValueAt(j, i));
                        dataArr[j][i] = valueAt;
                    }
                }
                Map<Object, Object> paramMap = ArrayUtils.toMap(dataArr);
                JenkinsClientUtils instance = JenkinsClientUtils.getInstance();
                JenkinsResponse build = instance.build(selectItem, paramMap);
                if (build.isSuccess()){
                    jenkinsDisplayText.setForeground(Color.BLUE);
                    jenkinsDisplayText.setText("Build Success !");
                }else {
                    jenkinsDisplayText.setForeground(Color.RED);
                    jenkinsDisplayText.setText("Build error !");

                    Notification notification = new Notification("",
                            AllIcons.Actions.Forward, NotificationType.WARNING);
                    notification.setContent(build.getErrorMsg());
                    notification.setTitle("Build Error ");
                    Notifications.Bus.notify(notification);
                }

            }
        });
        restParamBtn.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                initParamTable();
            }
        });
        jobListBox.addItemListener(new ItemListener() {
            /**
             * Invoked when an item has been selected or deselected by the user.
             * The code written for this method performs the operations
             * that need to occur when an item is selected (or deselected).
             *
             * @param e
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                int stateChange = e.getStateChange();
                if (ItemEvent.SELECTED == stateChange){
                    selectItem = e.getItem().toString();
                    initParamTable();
                }
            }
        });
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                init();
            }
        });
    }

    private void init() {
        buildBtn.setIcon(AllIcons.Actions.Compile);
        restParamBtn.setIcon(AllIcons.Actions.Restart);

        int init = JenkinsPropertiesComponent.isInit();
        if (init != 1){
            return;
        }
        String[][] paramArr = null;
        ParamTableModel<String> paramTableModel = new ParamTableModel<>(paramArr);
        buildParamTable.setModel(paramTableModel);
        buildParamTable.setRowHeight(18);

        initJobList();

        initParamTable();
    }


    private void initParamTable(){
        if (StringUtils.isEmpty(selectItem)){
            return;
        }
        ParamTableModel<String> model = (ParamTableModel<String>) buildParamTable.getModel();
        model.setRowCount(0);

        JenkinsClientUtils instance = JenkinsClientUtils.getInstance();
        JobEntity job = instance.getJob(selectItem);
        List<JobEntity.PropertyBean> property = job.getProperty();
        if (property.isEmpty()){
            return;
        }
        List<JobEntity.PropertyBean> collect = property.stream().filter(e -> e.get_class().equals("hudson.model.ParametersDefinitionProperty"))
                .collect(Collectors.toList());
        if (collect.isEmpty()){
            return;
        }
        List<JobEntity.PropertyBean.ParameterDefinitionsBeanX> parameterList = collect.get(0).getParameterDefinitions();
        if (!parameterList.isEmpty()){
            for (JobEntity.PropertyBean.ParameterDefinitionsBeanX param : parameterList) {
                model.addRow(new Object[]{param.getName(),param.getDefaultParameterValue().getValue()});
            }
        }
        buildParamTable.invalidate();
    }

    private void initJobList(){
        jobListBox.removeAllItems();
        JenkinsClientUtils instance = JenkinsClientUtils.getInstance();
        JobListEntity allJob = instance.getAllJob();
        List<JobListEntity.JobsBean> jobs = allJob.getJobs();
        if (!jobs.isEmpty()) {
            for (JobListEntity.JobsBean job : jobs) {
                jobListBox.addItem(job.getName());
            }
            selectItem = jobs.get(0).getName();
        }

    }

    public JPanel getContent(){
        return panel;
    }
}
