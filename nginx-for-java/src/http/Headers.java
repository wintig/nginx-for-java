package http;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author wintig
 * @Create 2018-11-09 上午2:30
 */
public class Headers {


    private String method;

    private String path;

    private String version;

    private Map<String, String> headerMap = new HashMap<>();

    public Headers() {

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void set(String key, String value) {
        headerMap.put(key, value);
    }

    public String get(String key) {
        return headerMap.get(key);
    }

    @Override
    public String toString() {
        return "Headers{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", headerMap=" + headerMap +
                '}';
    }
}
