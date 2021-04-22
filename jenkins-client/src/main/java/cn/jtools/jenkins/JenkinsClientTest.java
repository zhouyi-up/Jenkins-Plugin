package cn.jtools.jenkins;

/**
 * @author liujun
 */
public class JenkinsClientTest {

    public static void main(String[] args) {
        JenkinsClient jenkinsClient = JenkinsClient.builder()
                .host("http://127.0.0.1:8080/")
                .user("admin")
                .password("Corele1024.")
                .build();
        jenkinsClient.getJobList();
    }
}
