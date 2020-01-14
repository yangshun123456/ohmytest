package comm.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author : yangshun
 * @date : 2020/1/8 13:29
 */
public class JdbcUtil {
    private static Properties properties;
    private static InputStream is = null;
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    private JdbcUtil() {

    }

    static {
        try {
            properties = new Properties();
            is = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(is);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取connection
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        if (tl.get() != null) {
            return tl.get();
        }
        connection = DriverManager.getConnection(url, username, password);
        tl.set(connection);
        return tl.get();
    }

    //开启事务
    public static void startTransaction() throws SQLException {
        Connection connection = null;
        if (tl.get() != null) {
            connection = tl.get();
            connection.setAutoCommit(false);
        }
    }

    //提交事务
    public static void commit() throws SQLException {
        Connection connection = null;
        if (tl.get() != null) {
            connection = tl.get();
            connection.commit();
        }
    }

    //关闭连接
    public void close(){
        if(tl.get()!=null){
            try {
                tl.get().close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                tl.remove();
            }
        }
    }


}
