package comm.dao;

import comm.util.YsPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangshun
 * @date : 2020/1/9 15:52
 */
public class YsDao {

    private YsPool ysPool=null;
    private String type=null;
    private Connection connection=null;
    private PreparedStatement statement=null;
    private ResultSet resultSet=null;

    public YsDao(){
        ysPool=new YsPool();
    }


    public List<Object[]> select(String sql,Integer num)  {
        List<Object[]> list=new ArrayList<>();
        connection=ysPool.getCurrentConnection();
        try {
            statement=connection.prepareStatement(sql);
            resultSet=statement.executeQuery();
            while(resultSet.next()){
                Object[] objects=new Object[num];
                for(int i=0;i<num;i++){
                    Object object = resultSet.getObject(i+1);
                    objects[i]=object;
                }
                list.add(objects);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ysPool.release(connection);
        ysPool.close(statement,resultSet);
        return list;
    }

    public void delete(String sql){

    }

    public void update(String sql){

    }

    public void insert(String sql){

    }


}
