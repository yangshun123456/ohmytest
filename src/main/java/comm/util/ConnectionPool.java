package comm.util;

import comm.entity.DBbean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * @author : yangshun
 * @date : 2020/1/9 9:07
 */
public class ConnectionPool {
    // 连接池配置属性
    private DBbean dbBean;
    private boolean isActive = false; // 连接池活动状态
    private int contActive = 0;// 记录创建的总的连接数

    // 空闲连接
    private List<Connection> freeConnection = new Vector<Connection>();
    // 活动连接
    private List<Connection> activeConnection = new Vector<Connection>();

    // 将线程和连接绑定，保证事务能统一执行
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public ConnectionPool(DBbean dbBean) {
        super();
        this.dbBean = dbBean;
        init();
    }

    // 初始化
    public void init() {
        try {
            Class.forName(dbBean.getDriverName());
            for (int i = 0; i < dbBean.getInitConnections(); i++) {
                Connection conn;
                conn = newConnection();
                // 初始化最小连接数
                if (conn != null) {
                    freeConnection.add(conn);
                    contActive++;
                }
            }
            isActive = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获得当前连接
    public Connection getCurrentConnection(){
        // 默认线程里面取
        Connection conn = threadLocal.get();
        if(!isValid(conn)){
            conn = getConnection();
        }
        return conn;
    }

    // 获得连接
    public synchronized Connection getConnection() {
        Connection conn = null;
        try {
            // 判断是否超过最大连接数限制
            if(contActive < this.dbBean.getMaxActiveConnections()){
                if (freeConnection.size() > 0) {
                    conn = freeConnection.get(0);
                    if (conn != null) {
                        threadLocal.set(conn);
                    }
                    freeConnection.remove(0);
                } else {
                    conn = newConnection();
                    threadLocal.set(conn);
                }

            }else{
                // 继续获得连接,直到从新获得连接
                wait(this.dbBean.getConnTimeOut());
                conn = getConnection();
            }
            if (isValid(conn)) {
                activeConnection.add(conn);
                contActive ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 获得新连接
    private synchronized Connection newConnection()
            throws ClassNotFoundException, SQLException {
        Connection conn = null;
        if (dbBean != null) {
            Class.forName(dbBean.getDriverName());
            conn = DriverManager.getConnection(dbBean.getUrl(),
                    dbBean.getUserName(), dbBean.getPassword());
        }
        return conn;
    }

    // 释放连接
    public synchronized void releaseConn(Connection conn) throws SQLException {
        if (isValid(conn)&& !(freeConnection.size() > dbBean.getMaxConnections())) {
            freeConnection.add(conn);
            activeConnection.remove(conn);
            contActive --;
            threadLocal.remove();
            // 唤醒所有正待等待的线程，去抢连接
            notifyAll();
        }
    }

    // 判断连接是否可用
    private boolean isValid(Connection conn) {
        try {
            if (conn == null || conn.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // 销毁连接池
    public synchronized void destroy() {
        for (Connection conn : freeConnection) {
            try {
                if (isValid(conn)) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (Connection conn : activeConnection) {
            try {
                if (isValid(conn)) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        isActive = false;
        contActive = 0;
    }

    // 连接池状态
    public boolean isActive() {
        return isActive;
    }

}
