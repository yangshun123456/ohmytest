package connect.pool;

import connect.entity.PoolConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangShun
 * @Date 2020/1/13 15:05
 */
public class JdbcPool {
    //正在使用的连接
    private List<Connection> active=new ArrayList<>();
    //空闲的连接
    private List<Connection> free=new ArrayList<>();
    // 将线程和连接绑定，保证事务能统一执行
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
    //创建的连接数
    private Integer connectionNum=0;

    public JdbcPool(){
        init();
    }

    static {
        try {
            Class.forName(PoolConnect.getDriverName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        for(int i=0;i<PoolConnect.getInitConnections();i++){
            Connection connection=newConnection();
            if(connection!=null) {
                free.add(connection);
            }
        }
    }

    /**
     * 创建新连接
     * @return
     */
    public synchronized Connection newConnection()  {
        Connection connection=null;
        try {
            connection= DriverManager.getConnection(PoolConnect.getUrl(),PoolConnect.getUserName(),PoolConnect.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionNum++;
        return connection;
    }
    // 获得当前连接
    public Connection getCurrentConnection(){
        // 默认线程里面取
        Connection connection=threadLocal.get();
        if(!isUseFull(connection)){
            connection=getConnection();
        }
        return connection;
    }

    /**
     * 获取新连接
     * @return
     */
    public synchronized Connection getConnection(){
        Connection connection=null;
        if(connectionNum<PoolConnect.getMaxConnections()){
            if(free.size()>0){
                connection=free.get(0);
                threadLocal.set(connection);
                free.remove(0);
            }else {
                connection=newConnection();
                threadLocal.set(connection);
            }
        }else{
            try {
                wait();
                connection=getConnection();
                return connection;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(isUseFull(connection)){
            active.add(connection);
        }
        return connection;
    }

    /**
     * 释放连接
     * @param connection
     */
    public synchronized void release(Connection connection){
        if(isUseFull(connection)){
            free.add(connection);
            active.remove(connection);
            connectionNum--;
            threadLocal.remove();
            notifyAll();
        }
    }

    /**
     * 判断连接是否有用
     * @param connection
     * @return
     */
    public Boolean isUseFull(Connection connection){
        try {
            if(connection==null||connection.isClosed()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 销毁连接池
     */
    public void destroy(){
        for (Connection connection : active) {
            try {
                if(isUseFull(connection)) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for(Connection connection:free){
            try {
                if(isUseFull(connection)) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启事务
     */
    public void setAutoTransaction(){
        Connection connection=threadLocal.get();
        if(!isUseFull(connection)){
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提交事务
     */
    public void commit(){
        Connection connection=threadLocal.get();
        if(connection!=null){
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭流
     * @return
     */
    public void close(PreparedStatement statement, ResultSet resultSet){
        try {
            if(statement!=null)statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(resultSet!=null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
