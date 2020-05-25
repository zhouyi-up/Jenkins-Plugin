package com.jenkins.windows;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.jenkins.JenkinsPropertiesComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author liujun
 */
public class SettingWindow implements SearchableConfigurable, Configurable.NoScroll{

    private boolean isModified = false;

    private JPanel settingPanel;
    private JTextField jenkinsHostText;
    private JTextField jenkinsUserText;
    private JPasswordField jenkinsPwdText;
    private JCheckBox crumbEnable;

    public SettingWindow() {
        init();


    }

    private void init(){
        jenkinsUserText.getDocument().addDocumentListener(getDocumentListener());
        jenkinsPwdText.getDocument().addDocumentListener(getDocumentListener());
        jenkinsHostText.getDocument().addDocumentListener(getDocumentListener());

        crumbEnable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isModified = getChangeState();
            }
        });

        initJenkinsParam();
    }

    private void initJenkinsParam() {
        jenkinsHostText.setText(JenkinsPropertiesComponent.getHost());
        jenkinsUserText.setText(JenkinsPropertiesComponent.getUser());
        jenkinsPwdText.setText(JenkinsPropertiesComponent.getPwd());
        crumbEnable.setSelected(JenkinsPropertiesComponent.getCrumbEnable());
    }

    @NotNull
    private DocumentListener getDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isModified = getChangeState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isModified = getChangeState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isModified = getChangeState();
            }
        };
    }

    private boolean getChangeState(){
        if (jenkinsHostText.getText().equals(JenkinsPropertiesComponent.getHost())
                && jenkinsUserText.getText().equals(JenkinsPropertiesComponent.getUser())
                && getPwd().equals(JenkinsPropertiesComponent.getPwd())
                && crumbEnable.isSelected() == JenkinsPropertiesComponent.getCrumbEnable()
        ){
            return false;
        }
        return true;
    }

    /**
     * Unique configurable id.
     * Note this id should be THE SAME as the one specified in XML.
     *
     */
    @NotNull
    @Override
    public String getId() {
        return "jenkins";
    }

    /**
     * Returns the visible name of the configurable component.
     * Note, that this method must return the display name
     * that is equal to the display name declared in XML
     * to avoid unexpected errors.
     *
     * @return the visible name of the configurable component
     */
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Jenkins Plugin";
    }

    /**
     * Creates new Swing form that enables user to configure the settings.
     * Usually this method is called on the EDT, so it should not take a long time.
     * <p>
     * Also this place is designed to allocate resources (subscriptions/listeners etc.)
     *
     * @return new Swing form to show, or {@code null} if it cannot be created
     * @see #disposeUIResources
     */
    @Nullable
    @Override
    public JComponent createComponent() {

        return settingPanel;
    }

    /**
     * Indicates whether the Swing form was modified or not.
     * This method is called very often, so it should not take a long time.
     *
     * @return {@code true} if the settings were modified, {@code false} otherwise
     */
    @Override
    public boolean isModified() {
        return isModified;
    }

    /**
     * Stores the settings from the Swing form to the configurable component.
     * This method is called on EDT upon user's request.
     *
     * @throws ConfigurationException if values cannot be applied
     */
    @Override
    public void apply() throws ConfigurationException {
        String host = jenkinsHostText.getText();
        String pwd = getPwd();
        String user = jenkinsUserText.getText();

        JenkinsPropertiesComponent.setHost(host);
        JenkinsPropertiesComponent.setUser(user);
        JenkinsPropertiesComponent.setPwd(pwd);
        JenkinsPropertiesComponent.setCrumbEnable(crumbEnable.isSelected());

        isModified = false;
        JenkinsPropertiesComponent.setInit();
    }

    /**
     * Loads the settings from the configurable component to the Swing form.
     * This method is called on EDT immediately after the form creation or later upon user's request.
     */
    @Override
    public void reset() {
        initJenkinsParam();
    }

    /**
     * Called when 'Cancel' is pressed in the Settings dialog.
     */
    @Override
    public void cancel() {

    }

    /**
     * Notifies the configurable component that the Swing form will be closed.
     * This method should dispose all resources associated with the component.
     */
    @Override
    public void disposeUIResources() {

    }

    @NotNull
    private String getPwd() {
        return jenkinsPwdText.getText();
    }
}
