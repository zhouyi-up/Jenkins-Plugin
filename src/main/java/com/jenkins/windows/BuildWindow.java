package com.jenkins.windows;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.jenkins.JenkinsPropertiesComponent;
import com.jenkins.JenkinsResponse;
import com.jenkins.client.BuildParam;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
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
    private JPanel selectPane;
    private JLabel titleBtn;

    private String selectItem;

    private JenkinsClientAsync clientAsync;

    public BuildWindow() {
        clientAsync = new JenkinsClientAsync(JenkinsPropertiesComponent.getHost(),
                JenkinsPropertiesComponent.getUser(),
                JenkinsPropertiesComponent.getPwd());

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

                if (paramMap == null || paramMap.isEmpty()){
                    clientAsync.build(selectItem, new DefaultCallback() {
                        @Override
                        public void success(Object data) {
                            buildSuccess(Color.BLUE, "Build Success !");
                        }

                        @Override
                        public void error(Exception exception) {
                            buildSuccess(Color.RED, "Build error !");

                            Notification notification = new Notification("",
                                    AllIcons.Actions.Forward, NotificationType.WARNING);
                            notification.setContent(exception.getMessage());
                            notification.setTitle("Build Error ");
                            Notifications.Bus.notify(notification);
                        }
                    });
                }else {
                    BuildParam buildParam = new BuildParam();
                    buildParam.setJobName(selectItem);
                    buildParam.addParamForObject(paramMap);

                    clientAsync.build(buildParam, new DefaultCallback() {
                        @Override
                        public void success(Object data) {
                            buildSuccess(Color.BLUE, "Build Success !");
                        }

                        @Override
                        public void error(Exception exception) {
                            buildSuccess(Color.RED, "Build error !");

                            Notification notification = new Notification("",
                                    AllIcons.Actions.Forward, NotificationType.WARNING);
                            notification.setContent(exception.getMessage());
                            notification.setTitle("Build Error ");
                            Notifications.Bus.notify(notification);
                        }
                    });
                }
            }

            private void buildSuccess(Color blue, String s) {
                jenkinsDisplayText.setForeground(blue);
                jenkinsDisplayText.setText(s);
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

        clientAsync.jobInfo(selectItem, new DefaultCallback<JobEntity>() {
            @Override
            public void success(JobEntity data) {
                List<JobEntity.PropertyBean> property = data.getProperty();
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
        });
    }

    private void initJobList(){
        jobListBox.removeAllItems();

        clientAsync.jobList(new DefaultCallback<JobListEntity>() {
            @Override
            public void success(JobListEntity data) {
                List<JobListEntity.JobsBean> jobs = data.getJobs();
                if (!jobs.isEmpty()) {
                    for (JobListEntity.JobsBean job : jobs) {
                        jobListBox.addItem(job.getName());
                    }
                    selectItem = jobs.get(0).getName();
                }
            }
        });
    }

    public JPanel getContent(){
        return panel;
    }
}
