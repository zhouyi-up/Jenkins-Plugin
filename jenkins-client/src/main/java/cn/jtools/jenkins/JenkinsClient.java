package cn.jtools.jenkins;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author liujun
 */
public class JenkinsClient {

    private String host;
    private String user;
    private String password;

    public void getJobList(){
        try {
            String url = host + JenkinsConstants.Url.JOB_LIST;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(HttpMethod.GET.toString());
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", JenkinsUtils.auth(user, password));

            connection.connect();

            StringBuilder stringBuffer = new StringBuilder();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            int len;

            char[] buf = new char[1024];
            while ((len = reader.read(buf))!= -1){
                stringBuffer.append(buf, 0, len);
            }
            reader.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Builder{

        private String host;
        private String user;
        private String password;

        public Builder host(String host){
            this.host = host;
            return this;
        }
        public Builder user(String user){
            this.user = user;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }

        public JenkinsClient build(){
            JenkinsClient jenkinsClient = new JenkinsClient();
            jenkinsClient.setHost(host);
            jenkinsClient.setUser(user);
            jenkinsClient.setPassword(password);
            return jenkinsClient;
        }
    }
}
