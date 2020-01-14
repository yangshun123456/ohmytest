package comm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yangshun
 * @date : 2020/1/9 17:35
 */
public class SqlUtil {
    /**
     * sql语句
     */
    private String sql;
    /**
     * 几个字段
     */
    private Integer num;
    /**
     * 需要的set方法集合
     */
    private Map<String,Object> methodName;
    /**
     * 需要赋值的属性
     */
    private String col[];

    private Class clazz;

    public SqlUtil(Class clazz) {
        this.clazz = clazz;
    }

    public void findAll(String[] cols) {
        Map<String, Object> map = new HashMap<>();

        String name = clazz.getName();
        String[] split = name.split("\\.");
        String table = split[split.length - 1].toLowerCase();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        for (int i = 0; i < cols.length; i++) {
            if (i != cols.length - 1) {
                sql.append(cols[i] + ",");
            } else {
                sql.append(cols[i] + " ");
            }
        }
        sql.append("from " + table);
        //返回拼接后sql语句
        map.put("sql", sql.toString());
        List<String> list=new ArrayList<>();
        for (String col : cols) {
            String head = col.substring(0, 0);
            String next = col.substring(1, col.length() - 1);
            String max = head.toUpperCase();
            String end = "set"+max + next;
            list.add(end);
        }

        this.sql=sql.toString();
        this.num=cols.length;
        this.col=cols;
    }


}
