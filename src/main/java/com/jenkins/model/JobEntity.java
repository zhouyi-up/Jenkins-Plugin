package com.jenkins.model;

import java.util.List;

/**
 * @author liujun
 */
public class JobEntity {

    private String _class;
    private String description;
    private String displayName;
    private Object displayNameOrNull;
    private String fullDisplayName;
    private String fullName;
    private String name;
    private String url;
    private boolean buildable;
    private String color;
    private FirstBuildBean firstBuild;
    private boolean inQueue;
    private boolean keepDependencies;
    private LastBuildBean lastBuild;
    private LastCompletedBuildBean lastCompletedBuild;
    private Object lastFailedBuild;
    private LastStableBuildBean lastStableBuild;
    private LastSuccessfulBuildBean lastSuccessfulBuild;
    private Object lastUnstableBuild;
    private Object lastUnsuccessfulBuild;
    private int nextBuildNumber;
    private Object queueItem;
    private boolean concurrentBuild;
    private Object labelExpression;
    private ScmBean scm;
    private List<ActionsBean> actions;
    private List<BuildsBean> builds;
    private List<HealthReportBean> healthReport;
    private List<PropertyBean> property;
    private List<?> downstreamProjects;
    private List<?> upstreamProjects;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Object getDisplayNameOrNull() {
        return displayNameOrNull;
    }

    public void setDisplayNameOrNull(Object displayNameOrNull) {
        this.displayNameOrNull = displayNameOrNull;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public boolean isBuildable() {
        return buildable;
    }

    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public FirstBuildBean getFirstBuild() {
        return firstBuild;
    }

    public void setFirstBuild(FirstBuildBean firstBuild) {
        this.firstBuild = firstBuild;
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public boolean isKeepDependencies() {
        return keepDependencies;
    }

    public void setKeepDependencies(boolean keepDependencies) {
        this.keepDependencies = keepDependencies;
    }

    public LastBuildBean getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(LastBuildBean lastBuild) {
        this.lastBuild = lastBuild;
    }

    public LastCompletedBuildBean getLastCompletedBuild() {
        return lastCompletedBuild;
    }

    public void setLastCompletedBuild(LastCompletedBuildBean lastCompletedBuild) {
        this.lastCompletedBuild = lastCompletedBuild;
    }

    public Object getLastFailedBuild() {
        return lastFailedBuild;
    }

    public void setLastFailedBuild(Object lastFailedBuild) {
        this.lastFailedBuild = lastFailedBuild;
    }

    public LastStableBuildBean getLastStableBuild() {
        return lastStableBuild;
    }

    public void setLastStableBuild(LastStableBuildBean lastStableBuild) {
        this.lastStableBuild = lastStableBuild;
    }

    public LastSuccessfulBuildBean getLastSuccessfulBuild() {
        return lastSuccessfulBuild;
    }

    public void setLastSuccessfulBuild(LastSuccessfulBuildBean lastSuccessfulBuild) {
        this.lastSuccessfulBuild = lastSuccessfulBuild;
    }

    public Object getLastUnstableBuild() {
        return lastUnstableBuild;
    }

    public void setLastUnstableBuild(Object lastUnstableBuild) {
        this.lastUnstableBuild = lastUnstableBuild;
    }

    public Object getLastUnsuccessfulBuild() {
        return lastUnsuccessfulBuild;
    }

    public void setLastUnsuccessfulBuild(Object lastUnsuccessfulBuild) {
        this.lastUnsuccessfulBuild = lastUnsuccessfulBuild;
    }

    public int getNextBuildNumber() {
        return nextBuildNumber;
    }

    public void setNextBuildNumber(int nextBuildNumber) {
        this.nextBuildNumber = nextBuildNumber;
    }

    public Object getQueueItem() {
        return queueItem;
    }

    public void setQueueItem(Object queueItem) {
        this.queueItem = queueItem;
    }

    public boolean isConcurrentBuild() {
        return concurrentBuild;
    }

    public void setConcurrentBuild(boolean concurrentBuild) {
        this.concurrentBuild = concurrentBuild;
    }

    public Object getLabelExpression() {
        return labelExpression;
    }

    public void setLabelExpression(Object labelExpression) {
        this.labelExpression = labelExpression;
    }

    public ScmBean getScm() {
        return scm;
    }

    public void setScm(ScmBean scm) {
        this.scm = scm;
    }

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public List<BuildsBean> getBuilds() {
        return builds;
    }

    public void setBuilds(List<BuildsBean> builds) {
        this.builds = builds;
    }

    public List<HealthReportBean> getHealthReport() {
        return healthReport;
    }

    public void setHealthReport(List<HealthReportBean> healthReport) {
        this.healthReport = healthReport;
    }

    public List<PropertyBean> getProperty() {
        return property;
    }

    public void setProperty(List<PropertyBean> property) {
        this.property = property;
    }

    public List<?> getDownstreamProjects() {
        return downstreamProjects;
    }

    public void setDownstreamProjects(List<?> downstreamProjects) {
        this.downstreamProjects = downstreamProjects;
    }

    public List<?> getUpstreamProjects() {
        return upstreamProjects;
    }

    public void setUpstreamProjects(List<?> upstreamProjects) {
        this.upstreamProjects = upstreamProjects;
    }

    public static class FirstBuildBean {
        /**
         * _class : hudson.model.FreeStyleBuild
         * number : 1
         * url : http://jenkins.corele.top/job/test-job/1/
         */

        private String _class;
        private int number;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class LastBuildBean {
        /**
         * _class : hudson.model.FreeStyleBuild
         * number : 7
         * url : http://jenkins.corele.top/job/test-job/7/
         */

        private String _class;
        private int number;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class LastCompletedBuildBean {
        /**
         * _class : hudson.model.FreeStyleBuild
         * number : 7
         * url : http://jenkins.corele.top/job/test-job/7/
         */

        private String _class;
        private int number;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class LastStableBuildBean {
        /**
         * _class : hudson.model.FreeStyleBuild
         * number : 7
         * url : http://jenkins.corele.top/job/test-job/7/
         */

        private String _class;
        private int number;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class LastSuccessfulBuildBean {
        /**
         * _class : hudson.model.FreeStyleBuild
         * number : 7
         * url : http://jenkins.corele.top/job/test-job/7/
         */

        private String _class;
        private int number;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ScmBean {
        /**
         * _class : hudson.scm.NullSCM
         */

        private String _class;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }
    }

    public static class ActionsBean {
        /**
         * _class : hudson.model.ParametersDefinitionProperty
         * parameterDefinitions : [{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","value":"test"},"description":"","name":"TEST","type":"StringParameterDefinition"}]
         */

        private String _class;
        private List<ParameterDefinitionsBean> parameterDefinitions;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public List<ParameterDefinitionsBean> getParameterDefinitions() {
            return parameterDefinitions;
        }

        public void setParameterDefinitions(List<ParameterDefinitionsBean> parameterDefinitions) {
            this.parameterDefinitions = parameterDefinitions;
        }

        public static class ParameterDefinitionsBean {
            /**
             * _class : hudson.model.StringParameterDefinition
             * defaultParameterValue : {"_class":"hudson.model.StringParameterValue","value":"test"}
             * description :
             * name : TEST
             * type : StringParameterDefinition
             */

            private String _class;
            private DefaultParameterValueBean defaultParameterValue;
            private String description;
            private String name;
            private String type;

            public String get_class() {
                return _class;
            }

            public void set_class(String _class) {
                this._class = _class;
            }

            public DefaultParameterValueBean getDefaultParameterValue() {
                return defaultParameterValue;
            }

            public void setDefaultParameterValue(DefaultParameterValueBean defaultParameterValue) {
                this.defaultParameterValue = defaultParameterValue;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class DefaultParameterValueBean {
                /**
                 * _class : hudson.model.StringParameterValue
                 * value : test
                 */

                private String _class;
                private String value;

                public String get_class() {
                    return _class;
                }

                public void set_class(String _class) {
                    this._class = _class;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }

    public static class BuildsBean {
        /**
         * _class : hudson.model.FreeStyleBuild
         * number : 7
         * url : http://jenkins.corele.top/job/test-job/7/
         */

        private String _class;
        private int number;
        private String url;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class HealthReportBean {
        /**
         * description : Build stability: No recent builds failed.
         * iconClassName : icon-health-80plus
         * iconUrl : health-80plus.png
         * score : 100
         */

        private String description;
        private String iconClassName;
        private String iconUrl;
        private int score;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconClassName() {
            return iconClassName;
        }

        public void setIconClassName(String iconClassName) {
            this.iconClassName = iconClassName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    public static class PropertyBean {
        /**
         * _class : hudson.plugins.jira.JiraProjectProperty
         * parameterDefinitions : [{"_class":"hudson.model.StringParameterDefinition","defaultParameterValue":{"_class":"hudson.model.StringParameterValue","name":"TEST","value":"test"},"description":"","name":"TEST","type":"StringParameterDefinition"}]
         */

        private String _class;
        private List<ParameterDefinitionsBeanX> parameterDefinitions;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public List<ParameterDefinitionsBeanX> getParameterDefinitions() {
            return parameterDefinitions;
        }

        public void setParameterDefinitions(List<ParameterDefinitionsBeanX> parameterDefinitions) {
            this.parameterDefinitions = parameterDefinitions;
        }

        public static class ParameterDefinitionsBeanX {
            /**
             * _class : hudson.model.StringParameterDefinition
             * defaultParameterValue : {"_class":"hudson.model.StringParameterValue","name":"TEST","value":"test"}
             * description :
             * name : TEST
             * type : StringParameterDefinition
             */

            private String _class;
            private DefaultParameterValueBeanX defaultParameterValue;
            private String description;
            private String name;
            private String type;

            public String get_class() {
                return _class;
            }

            public void set_class(String _class) {
                this._class = _class;
            }

            public DefaultParameterValueBeanX getDefaultParameterValue() {
                return defaultParameterValue;
            }

            public void setDefaultParameterValue(DefaultParameterValueBeanX defaultParameterValue) {
                this.defaultParameterValue = defaultParameterValue;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public static class DefaultParameterValueBeanX {
                /**
                 * _class : hudson.model.StringParameterValue
                 * name : TEST
                 * value : test
                 */

                private String _class;
                private String name;
                private String value;

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

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}
