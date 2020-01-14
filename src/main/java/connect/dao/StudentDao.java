package connect.dao;

import comm.entity.Student;
import connect.entity.DataProperties;

/**
 * @Author YangShun
 * @Date 2020/1/13 18:55
 */
public class StudentDao extends BaseDao<Student>{
    @Override
    public Class<Student> getClazz() {
        return Student.class;
    }

    @Override
    public DataProperties getProperties() {
        String[] cols={"id_","name"};
        dataProperties.setClazz(getClazz());
        dataProperties.setCols(cols);
        return dataProperties;
    }
}
