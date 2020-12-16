package com.jenkins.ui;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.jenkins.client.BuildParam;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.model.JobEntity;
import com.jenkins.windows.ParamTableModel;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author liujun
 */
public class JenkinsBuildView extends JFrame {

    private static final Vector<String> title = new Vector<>();

    private JenkinsClientAsync jenkinsClientAsync;
    private JobEntity jobEntity;
    JBPanel jbPanel;
    JBTable jbTable;
    DefaultTableModel defaultTableModel;


    public JenkinsBuildView(JenkinsClientAsync jenkinsClientAsync, JobEntity jobEntity){
        this.jenkinsClientAsync = jenkinsClientAsync;
        this.jobEntity = jobEntity;
        title.add("Param");
        title.add("Value");

        jbPanel = new JBPanel();
        jbPanel.setLayout(new BorderLayout());

        initParamTable();
        initBtnPanel(jenkinsClientAsync, jobEntity);

        add(jbPanel);
        setSize(300,300);
        setLocationRelativeTo(null);
        setVisible(true);
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void dispose() {
        jbTable = null;
        jbPanel = null;
        defaultTableModel = null;
        super.dispose();
    }

    /**
     * 初始化按钮面板
     * @param jenkinsClientAsync
     * @param jobEntity
     */
    private void initBtnPanel(JenkinsClientAsync jenkinsClientAsync, JobEntity jobEntity) {
        JBPanel btnPanel = new JBPanel();
        JButton buildBtn = new JButton("Build");
        buildBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int columnCount = defaultTableModel.getColumnCount();
                int rowCount = defaultTableModel.getRowCount();

                String[][] dataArr = new String[rowCount][columnCount];
                for (int i = 0; i < columnCount; i++) {
                    for (int j = 0; j < rowCount; j++) {
                        String valueAt = String.valueOf(defaultTableModel.getValueAt(j, i));
                        dataArr[j][i] = valueAt;
                    }
                }

                Map<Object, Object> paramMap = ArrayUtils.toMap(dataArr);

                BuildParam buildParam = new BuildParam();
                buildParam.setJobName(jobEntity.getName());
                buildParam.addParamForObject(paramMap);

                jenkinsClientAsync.build(buildParam, new DefaultCallback() {
                    @Override
                    public void success(Object data) {
                        dispose();
                    }

                    @Override
                    public void error(Exception exception) {

                    }
                });
            }
        });

        btnPanel.add(buildBtn);
        jbPanel.add(btnPanel,BorderLayout.SOUTH);
    }

    /**
     * 初始化参数面板
     */
    private void initParamTable() {

        defaultTableModel = new DefaultTableModel(getParam(), title);
        jbTable = new JBTable(defaultTableModel);

        JBScrollPane jbScrollPane = new JBScrollPane(jbTable);
        jbPanel.add(jbScrollPane, BorderLayout.CENTER);
    }

    /**
     * 处理参数
     * @return
     */
    private Vector<Vector<String>> getParam(){
        Vector<Vector<String>> vectors = new Vector<>();

        List<JobEntity.PropertyBean> property = jobEntity.getProperty();
        if (property.isEmpty()){
            return vectors;
        }
        List<JobEntity.PropertyBean> collect = property.stream().filter(e -> e.get_class().equals("hudson.model.ParametersDefinitionProperty"))
                .collect(Collectors.toList());
        if (collect.isEmpty()){
            return vectors;
        }
        List<JobEntity.PropertyBean.ParameterDefinitionsBeanX> parameterList = collect.get(0).getParameterDefinitions();
        if (!parameterList.isEmpty()){
            for (JobEntity.PropertyBean.ParameterDefinitionsBeanX param : parameterList) {
                Vector<String> vector = new Vector<>();
                vector.add(param.getName());
                vector.add(param.getDefaultParameterValue().getValue());

                vectors.add(vector);
            }
        }

        return vectors;
    }
}
