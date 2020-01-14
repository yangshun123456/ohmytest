package connect.util;

import connect.entity.DataProperties;
import connect.pool.JdbcPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取数据list或者操作
 * @Author YangShun
 * @Date 2020/1/13 18:07
 */
public class JdbcUtil {
    private JdbcPool Pool=null;
    private Connection connection=null;
    private PreparedStatement statement=null;
    private ResultSet resultSet=null;
    private JdbcPool jdbcPool=new JdbcPool();
    private DataProperties dataProperties;

    public JdbcUtil(DataProperties dataProperties){
        this.dataProperties=dataProperties;
    }

    public List<Object[]> select()  {
        String sql= dataProperties.getSql();
        Integer num= dataProperties.getCols().length;
        List<Object[]> list=new ArrayList<>();
        connection=jdbcPool.getCurrentConnection();
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
        }finally {
            jdbcPool.release(connection);
            jdbcPool.close(statement,resultSet);
        }

        return list;
    }

    public void delete(){
        String sql=dataProperties.getSql();
        connection=jdbcPool.getCurrentConnection();
        try {
            statement=connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbcPool.release(connection);
            jdbcPool.close(statement,resultSet);
        }
    }
}
