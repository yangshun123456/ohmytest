package connect.entity;

import lombok.Data;

import java.util.Map;

/**
 * @Author YangShun
 * @Date 2020/1/13 13:15
 */
@Data
public class DataProperties {
    /**
     * sql语句
     */
    private String sql;

    /**
     * 传入字段数组 如{id，name}  **必填
     */
    private String[] cols;

    /**
     * 字段对应set方法
     */
    private Map<String ,String> methods;

    /**
     * 需要操作的类  **必填
     */
    private Class<?> clazz;


}
