package com.jenkins.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author liujun
 */
public class JobBuildInfo {


    /**
     * _class : hudson.model.FreeStyleBuild
     * actions : [{"_class":"hudson.model.ParametersAction","parameters":[{"_class":"hudson.model.StringParameterValue","name":"TestParam","value":"TestParam"},{"_class":"hudson.model.BooleanParameterValue","name":"P1","value":true},{"_class":"hudson.model.TextParameterValue","name":"P3","value":"P3"}]},{"_class":"hudson.model.CauseAction","causes":[{"_class":"hudson.model.Cause$UserIdCause","shortDescription":"Started by user admin","userId":"admin","userName":"admin"}]},{},{"_class":"org.jenkinsci.plugins.displayurlapi.actions.RunDisplayAction"}]
     * artifacts : []
     * building : false
     * description : null
     * displayName : #8
     * duration : 284
     * estimatedDuration : 206
     * executor : null
     * fullDisplayName : Demo-A #8
     * id : 8
     * keepLog : false
     * number : 8
     * queueId : 8
     * result : SUCCESS
     * timestamp : 1608271270397
     * url : http://127.0.0.1:8080/job/Demo-A/8/
     * builtOn :
     * changeSet : {"_class":"hudson.scm.EmptyChangeLogSet","items":[],"kind":null}
     * culprits : []
     */

    private String _class;
    private boolean building;
    private Object description;
    private String displayName;
    private int duration;
    private int estimatedDuration;
    private Object executor;
    private String fullDisplayName;
    private String id;
    private boolean keepLog;
    private int number;
    private int queueId;
    private String result;
    private long timestamp;
    private String url;
    private String builtOn;
    private ChangeSetBean changeSet;
    private List<ActionsBean> actions;
    private List<?> artifacts;
    private List<?> culprits;

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public Object getExecutor() {
        return executor;
    }

    public void setExecutor(Object executor) {
        this.executor = executor;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isKeepLog() {
        return keepLog;
    }

    public void setKeepLog(boolean keepLog) {
        this.keepLog = keepLog;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBuiltOn() {
        return builtOn;
    }

    public void setBuiltOn(String builtOn) {
        this.builtOn = builtOn;
    }

    public ChangeSetBean getChangeSet() {
        return changeSet;
    }

    public void setChangeSet(ChangeSetBean changeSet) {
        this.changeSet = changeSet;
    }

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public List<?> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<?> artifacts) {
        this.artifacts = artifacts;
    }

    public List<?> getCulprits() {
        return culprits;
    }

    public void setCulprits(List<?> culprits) {
        this.culprits = culprits;
    }

    public static class ChangeSetBean implements Serializable {
        /**
         * _class : hudson.scm.EmptyChangeLogSet
         * items : []
         * kind : null
         */

        private String _class;
        private Object kind;
        private List<?> items;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public Object getKind() {
            return kind;
        }

        public void setKind(Object kind) {
            this.kind = kind;
        }

        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }
    }

    public static class ActionsBean implements Serializable {
        /**
         * _class : hudson.model.ParametersAction
         * parameters : [{"_class":"hudson.model.StringParameterValue","name":"TestParam","value":"TestParam"},{"_class":"hudson.model.BooleanParameterValue","name":"P1","value":true},{"_class":"hudson.model.TextParameterValue","name":"P3","value":"P3"}]
         * causes : [{"_class":"hudson.model.Cause$UserIdCause","shortDescription":"Started by user admin","userId":"admin","userName":"admin"}]
         */

        private String _class;
        private List<ParametersBean> parameters;
        private List<CausesBean> causes;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public List<ParametersBean> getParameters() {
            return parameters;
        }

        public void setParameters(List<ParametersBean> parameters) {
            this.parameters = parameters;
        }

        public List<CausesBean> getCauses() {
            return causes;
        }

        public void setCauses(List<CausesBean> causes) {
            this.causes = causes;
        }

        public static class ParametersBean implements Serializable {
            /**
             * _class : hudson.model.StringParameterValue
             * name : TestParam
             * value : TestParam
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

        public static class CausesBean implements Serializable {
            /**
             * _class : hudson.model.Cause$UserIdCause
             * shortDescription : Started by user admin
             * userId : admin
             * userName : admin
             */

            private String _class;
            private String shortDescription;
            private String userId;
            private String userName;

            public String get_class() {
                return _class;
            }

            public void set_class(String _class) {
                this._class = _class;
            }

            public String getShortDescription() {
                return shortDescription;
            }

            public void setShortDescription(String shortDescription) {
                this.shortDescription = shortDescription;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
