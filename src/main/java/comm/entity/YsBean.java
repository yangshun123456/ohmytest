package comm.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : yangshun
 * @date : 2020/1/9 13:32
 */
@Setter
@Getter
public class YsBean {
    private static String driverName;
    private static String userName;
    private static String url;
    private static String password;

    private static Integer initConnections;//最低连接数
    private static Integer maxActiveConnections;//最大活动连接数
    private static Integer maxConnections;//最大连接数

    private static Integer connTimeOut;//连接时间
    static {
        Properties properties=new Properties();
        InputStream is=DBbean.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverName=properties.getProperty("driver");
        url=properties.getProperty("url");
        userName=properties.getProperty("username");
        password=properties.getProperty("password");
        initConnections=Integer.parseInt(properties.getProperty("initConnections"));
        maxActiveConnections=Integer.parseInt(properties.getProperty("maxActiveConnections"));
        maxConnections=Integer.parseInt(properties.getProperty("maxConnections"));
    }
    public YsBean(){
        System.out.println(12345);
    }

    public static String getDriverName() {
        return driverName;
    }

    public static void setDriverName(String driverName) {
        YsBean.driverName = driverName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        YsBean.userName = userName;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        YsBean.url = url;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        YsBean.password = password;
    }

    public static Integer getInitConnections() {
        return initConnections;
    }

    public static void setInitConnections(Integer initConnections) {
        YsBean.initConnections = initConnections;
    }

    public static Integer getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public static void setMaxActiveConnections(Integer maxActiveConnections) {
        YsBean.maxActiveConnections = maxActiveConnections;
    }

    public static Integer getMaxConnections() {
        return maxConnections;
    }

    public static void setMaxConnections(Integer maxConnections) {
        YsBean.maxConnections = maxConnections;
    }

    public static Integer getConnTimeOut() {
        return connTimeOut;
    }

    public static void setConnTimeOut(Integer connTimeOut) {
        YsBean.connTimeOut = connTimeOut;
    }
}
