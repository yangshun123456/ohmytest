package comm.dao;

import comm.util.SqlUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangshun
 * @date : 2020/1/8 15:54
 */
public abstract class BaseDao<T> {
    private String sql;//sql语句
    private Integer num;//字段数
    private List<String> list;//需要的set方法集合
    private String[] cols;//属性名

    public abstract Class<T> getClazz();

    public String getClassName(){
        String name=getClazz().getName();
        String[] split = name.split("\\.");
        return split[split.length];
    }

    public List<T> findAll(SqlUtil sqlUtil) throws IllegalAccessException, InstantiationException, NoSuchMethodException {

        List<T> list1=new ArrayList<>();
        List<Object[]> objects = new YsDao().select(sql, this.num);
        T t = (T) getClazz().newInstance();
        Field[] declaredFields = getClazz().getDeclaredFields();
        List<Method> methods=new ArrayList<>();
        if(declaredFields!=null&&cols!=null&&cols.length!=0) {
            for (Field declaredField : declaredFields) {
                for (String col : cols) {
                    if (declaredField.getName().equals(col)) {
                        Method method = getClazz().getDeclaredMethod(declaredField.getName(), declaredField.getType());
                        methods.add(method);
                    }
                }
            }
        }
        for (Method method : methods) {

        }

        return list1;
    }
}
