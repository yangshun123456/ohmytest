package connect.dao;

import comm.entity.Grade;
import connect.entity.DataProperties;

/**
 * @Author YangShun
 * @Date 2020/1/14 16:35
 */
public class GradeDao extends BaseDao<Grade> {
    @Override
    public Class<Grade> getClazz() {
        return Grade.class;
    }

    @Override
    public DataProperties getProperties() {
        String[] cols={"id_","name","num"};
        dataProperties.setClazz(getClazz());
        dataProperties.setCols(cols);
        return dataProperties;
    }
}
