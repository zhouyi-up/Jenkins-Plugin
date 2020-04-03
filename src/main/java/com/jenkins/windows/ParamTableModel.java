package com.jenkins.windows;

import javax.swing.table.DefaultTableModel;

/**
 * @author liujun
 */
public class ParamTableModel<T> extends DefaultTableModel {

    private static final String[] header = {"param","value"};

    public ParamTableModel(T[][] paramArr){
        super(paramArr,header);
    }

    /**
     * Returns true regardless of parameter values.
     *
     * @param row    the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true
     * @see #setValueAt
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 0){
            return false;
        }
        return true;
    }
}
