package com.jenkins.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author liujun
 */
public class JobListEntity implements Serializable {

    private String _class;
    private String mode;
    private String nodeDescription;
    private String nodeName;
    private int numExecutors;
    private String description;
    private PrimaryViewBean primaryView;
    private boolean quietingDown;
    private int slaveAgentPort;
    private UnlabeledLoadBean unlabeledLoad;
    private boolean useCrumbs;
    private boolean useSecurity;
    private List<AssignedLabelsBean> assignedLabels;
    private List<JobsBean> jobs;
    private List<ViewsBean> views;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getNumExecutors() {
        return numExecutors;
    }

    public void setNumExecutors(int numExecutors) {
        this.numExecutors = numExecutors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PrimaryViewBean getPrimaryView() {
        return primaryView;
    }

    public void setPrimaryView(PrimaryViewBean primaryView) {
        this.primaryView = primaryView;
    }

    public boolean isQuietingDown() {
        return quietingDown;
    }

    public void setQuietingDown(boolean quietingDown) {
        this.quietingDown = quietingDown;
    }

    public int getSlaveAgentPort() {
        return slaveAgentPort;
    }

    public void setSlaveAgentPort(int slaveAgentPort) {
        this.slaveAgentPort = slaveAgentPort;
    }

    public UnlabeledLoadBean getUnlabeledLoad() {
        return unlabeledLoad;
    }

    public void setUnlabeledLoad(UnlabeledLoadBean unlabeledLoad) {
        this.unlabeledLoad = unlabeledLoad;
    }

    public boolean isUseCrumbs() {
        return useCrumbs;
    }

    public void setUseCrumbs(boolean useCrumbs) {
        this.useCrumbs = useCrumbs;
    }

    public boolean isUseSecurity() {
        return useSecurity;
    }

    public void setUseSecurity(boolean useSecurity) {
        this.useSecurity = useSecurity;
    }

    public List<AssignedLabelsBean> getAssignedLabels() {
        return assignedLabels;
    }

    public void setAssignedLabels(List<AssignedLabelsBean> assignedLabels) {
        this.assignedLabels = assignedLabels;
    }

    public List<JobsBean> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobsBean> jobs) {
        this.jobs = jobs;
    }

    public List<ViewsBean> getViews() {
        return views;
    }

    public void setViews(List<ViewsBean> views) {
        this.views = views;
    }

    public static class PrimaryViewBean implements Serializable  {
        /**
         * _class : hudson.model.AllView
         * name : all
         * url : http://jenkins.corele.top/
         */

        private String _class;
        private String name;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class UnlabeledLoadBean implements Serializable {
        /**
         * _class : jenkins.model.UnlabeledLoadStatistics
         */

        private String _class;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }
    }

    public static class AssignedLabelsBean implements Serializable {
        /**
         * name : master
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class JobsBean implements Serializable {
        /**
         * _class : hudson.model.FreeStyleProject
         * name : j-tools
         * url : http://jenkins.corele.top/job/j-tools/
         * color : blue
         */

        private String _class;
        private String name;
        private String url;
        private String color;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class ViewsBean implements Serializable {
        /**
         * _class : hudson.model.AllView
         * name : all
         * url : http://jenkins.corele.top/
         */

        private String _class;
        private String name;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
