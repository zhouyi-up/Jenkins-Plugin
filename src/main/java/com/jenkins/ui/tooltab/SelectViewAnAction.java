package com.jenkins.ui.tooltab;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ex.CustomComponentAction;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.SearchTextField;
import com.intellij.ui.components.JBComboBoxLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/**
 * @author liujun
 */
public class SelectViewAnAction extends DumbAwareAction implements CustomComponentAction{

    JComboBox<String> viewComboBox;
    Listener listener;

    public SelectViewAnAction(){
    }

    public SelectViewAnAction(Listener listener){
        addListener(listener);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
    }

    @Override
    public @NotNull JComponent createCustomComponent(@NotNull Presentation presentation, @NotNull String place) {
        viewComboBox = new ComboBox<>();
        viewComboBox.addItem("None");
        viewComboBox.addActionListener(e -> {

        });
        viewComboBox.addItemListener(e -> {
            listener.actionPerformed(e);
        });
        return viewComboBox;
    }

    public void addItem(String item){
        viewComboBox.addItem(item);
    }

    public void removeChildAll(){
        viewComboBox.removeAll();
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
