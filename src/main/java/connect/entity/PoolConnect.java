package connect.entity;

import comm.entity.DBbean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author YangShun
 * @Date 2020/1/13 13:15
 */
public class PoolConnect {
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
        InputStream is= DBbean.class.getClassLoader().getResourceAsStream("jdbc.properties");
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

    public static String getDriverName() {
        return driverName;
    }

    public static void setDriverName(String driverName) {
        PoolConnect.driverName = driverName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        PoolConnect.userName = userName;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        PoolConnect.url = url;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        PoolConnect.password = password;
    }

    public static Integer getInitConnections() {
        return initConnections;
    }

    public static void setInitConnections(Integer initConnections) {
        PoolConnect.initConnections = initConnections;
    }

    public static Integer getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public static void setMaxActiveConnections(Integer maxActiveConnections) {
        PoolConnect.maxActiveConnections = maxActiveConnections;
    }

    public static Integer getMaxConnections() {
        return maxConnections;
    }

    public static void setMaxConnections(Integer maxConnections) {
        PoolConnect.maxConnections = maxConnections;
    }

    public static Integer getConnTimeOut() {
        return connTimeOut;
    }

    public static void setConnTimeOut(Integer connTimeOut) {
        PoolConnect.connTimeOut = connTimeOut;
    }
}
