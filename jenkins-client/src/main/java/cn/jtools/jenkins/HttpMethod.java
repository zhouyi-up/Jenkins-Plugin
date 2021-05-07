package cn.jtools.jenkins;

/**
 * @author liujun
 */
public enum HttpMethod {
    /**
     * 请求方法
     */
    POST, GET, PUT, DELETE;

    @Override
    public String toString(){
        return this.name();
    }
}
