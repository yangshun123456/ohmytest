package connect.dao;

import connect.entity.DataProperties;
import connect.util.DataUtil;
import connect.util.JdbcUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangShun
 * @Date 2020/1/13 15:51
 */
public abstract class BaseDao<T> {

    protected DataProperties dataProperties=new DataProperties();
    /**
     *获取类
     * @return
     */
    public abstract Class<T> getClazz();

    /**
     * 填写需要查的字段cols{}和clazz类
     * @return
     */
    public abstract DataProperties getProperties();

    /**
     * 查询所有
     * @return
     */
    public List<T> findAll(){
        //通过DataUtil的findAll()封装方法
        DataUtil dataUtil=new DataUtil(getProperties());
        DataProperties all = dataUtil.findAll();

        JdbcUtil jdbcUtil=new JdbcUtil(all);
        List<Object[]> select = jdbcUtil.select();
        List<T> findData=new ArrayList<>();
        for (Object[] objects : select) {
            List<Object> list=new ArrayList<>();
            for(int i=0;i<objects.length;i++){
                list.add(objects[i]);
            }
            T t = GetData(all, list);
            findData.add(t);
        }
        return findData;
    }

    /**
     * 根据id查询单条数据
     * @param id
     * @return
     */
    public T get(Integer id){
        DataUtil dataUtil=new DataUtil(getProperties());
        DataProperties dataProperties=dataUtil.get(id);

        JdbcUtil jdbcUtil=new JdbcUtil(dataProperties);
        List<Object[]> select = jdbcUtil.select();
        List<Object> list=new ArrayList<>();
        for (Object[] objects : select) {
            List<Object> list1=new ArrayList<>();
            for(int i=0;i<objects.length;i++){
                list1.add(objects[i]);
            }
            T t = GetData(dataProperties, list1);
            return t;
        }

        return null;
    }

    /**
     * 删除一条数据
     * @param id
     */
    public void delete(Integer id){
        DataUtil dataUtil=new DataUtil(getProperties());
        DataProperties delete = dataUtil.delete(id);
        JdbcUtil jdbcUtil=new JdbcUtil(delete);
        jdbcUtil.delete();
    }

    /**
     * 把数据由object转换成泛型
     * @param dataProperties
     * @param list
     * @return
     */
    public T GetData(DataProperties dataProperties,List<Object> list){
        Field[] declaredFields = getClazz().getDeclaredFields();
        T t=null;
        try {
            t=getClazz().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (Field declaredField : declaredFields) {
            for (int i=0;i<dataProperties.getCols().length;i++) {
                String col=dataProperties.getCols()[i];
                if(col.equals(declaredField.getName())){
                    try {
                        Method method=getClazz().getDeclaredMethod(
                                dataProperties.getMethods().get(col),
                                declaredField.getType());
                        method.invoke(t,list.get(i));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return t;
    }

}
