package comm.entity;

import lombok.Data;

/**
 * @author : yangshun
 * @date : 2020/1/9 9:11
 */
@Data
public class DBbean {
    private  String driverName;
    private  String userName;
    private  String url;
    private  String password;

    private  Integer initConnections;//最低连接数
    private  Integer maxActiveConnections;//最大活动连接数
    private  Integer maxConnections;//最大连接数

    private  Integer connTimeOut;//连接时间

    private  boolean isCheekPool;

}
