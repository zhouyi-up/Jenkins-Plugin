package com.jenkins.ui.tooltab;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.SearchTextField;
import com.intellij.ui.components.JBComboBoxLabel;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * @author liujun
 */
public class SelectViewAnAction extends DumbAwareAction implements CustomComponentAction{

    JComboBox<String> viewComboBox;
    Listener listener;

    List<String> itemList ;

    public SelectViewAnAction(Listener listener){
        addListener(listener);

        itemList = Lists.newArrayList();

        viewComboBox = new ComboBox<>();
        viewComboBox.addItem("None");
        viewComboBox.addActionListener(e -> {

        });
        viewComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                listener.actionPerformed(e);
            }
        });
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
    }

    @Override
    public @NotNull JComponent createCustomComponent(@NotNull Presentation presentation, @NotNull String place) {
        return viewComboBox;
    }

    public void addItem(String item){
        if (itemList.contains(item)) {
            return;
        }
        itemList.add(item);
        viewComboBox.addItem(item);
    }

    public void removeChildAll(){
        viewComboBox.removeAllItems();
        itemList.clear();
    }

    public String selectItem(){
        Object selectedItem = viewComboBox.getSelectedItem();
        return selectedItem == null ? "None" : String.valueOf(selectedItem);
    }

    public void addListener(Listener listener){
        this.listener = listener;
    }

    public static interface Listener{
        void actionPerformed(ItemEvent e);
    }
}
