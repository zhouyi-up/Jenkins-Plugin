package com.jenkins.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.jenkins.client.BuildParam;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.model.JobEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
public class JenkinsBuildView extends DialogWrapper {

    private static final Vector<String> TITLE = new Vector<>();

    static {
        TITLE.add("Param");
        TITLE.add("Value");
    }

    private final JobEntity jobEntity;


    JBPanel jbPanel;
    JBTable jbTable;
    DefaultTableModel defaultTableModel;


    public JenkinsBuildView(JobEntity jobEntity){
        super(true);
        this.jobEntity = jobEntity;

        jbPanel = new JBPanel();
        jbPanel.setLayout(new BorderLayout());

        initParamTable();

        setTitle("Build");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return jbPanel;
    }

    /**
     * 初始化参数面板
     */
    private void initParamTable() {

        defaultTableModel = new DefaultTableModel(getParam(), TITLE);
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

    public Map<Object,Object> getBuildParamMap(){
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
        return paramMap;
    }
}
