import comm.entity.Grade;
import comm.entity.Student;
import comm.util.JdbcUtil;
import comm.util.YsPool;
import connect.dao.GradeDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : yangshun
 * @date : 2020/1/8 14:01
 */
public class Test1 {
    private ApplicationContext applicationContext=null;

    @Before
    public void init(){
        applicationContext=new ClassPathXmlApplicationContext("application.xml");
    }

    @Test
    public void test() throws SQLException, InterruptedException {
        for(int i=0;i<10;i++){
            Connection connection= JdbcUtil.getConnection();
            System.out.println(connection);
        }
        System.out.println("-----------------------------");
        for(int i=0;i<10;i++){
            new Thread(()->{
                try {
                    Connection connection=JdbcUtil.getConnection();
                    System.out.println(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(10000);
    }

    @Test
    public void test2() throws InterruptedException {
        YsPool ysPool=new YsPool();
//        Connection connection=ysPool.getCurrentConnection();
//        Connection connection2=ysPool.getCurrentConnection();
//        System.out.println(connection);
//        System.out.println(connection2);
        System.out.println("剩余空闲"+ysPool.getFree().size());
        System.out.println("剩余活动"+ysPool.getActive().size());

        new Thread(()->{
            Connection connection=ysPool.getCurrentConnection();
            System.out.println(connection);
        }).start();

        new Thread(()->{
            Connection connection=ysPool.getCurrentConnection();
            System.out.println(connection);
        }).start();
        new Thread(()->{
            Connection connection=ysPool.getCurrentConnection();
            System.out.println(connection);
        }).start();

        new Thread(()->{
            Connection connection=ysPool.getCurrentConnection();
            System.out.println(connection);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ysPool.release(connection);
        }).start();

        new Thread(()->{
            Connection connection=ysPool.getCurrentConnection();
            System.out.println(connection);
        }).start();

        while (true){
            System.out.println("剩余空闲"+ysPool.getFree().size());
            System.out.println("剩余活动"+ysPool.getActive().size());
            Thread.sleep(2000);

        }

    }
    @Test
    public void test3(){
//        SqlUtil sqlUtil=new SqlUtil(Student.class);
//        String cols[]={"id","name"};
//        String sql = sqlUtil.findAll(cols);
//        YsDao ysDao=new YsDao();
//        List<Object[]> select = ysDao.select(sql, cols.length);
//
//        System.out.println(select);
    }

    @Test
    public void test4(){
        Field[] declaredFields = Student.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
        }
    }

    @Test
    public void test5() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class clazz=Student.class;
        Student student= (Student) clazz.newInstance();
        String typeName = clazz.getTypeName();
        Method method=clazz.getDeclaredMethod("setId", Integer.class);
        Method method1=clazz.getDeclaredMethod("setName", String.class);
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            Class<?> type = declaredField.getType();
        }
        Object object=1;
        method.invoke(student,object);
        method1.invoke(student,"nice");
        System.out.println(student);
    }

    @Test
    public void test6(){
//        StudentDao studentDao=new StudentDao();
//        List<Student> all = studentDao.findAll();
//        System.out.println(all);
        GradeDao gradeDao=new GradeDao();
//        List<Grade> list=gradeDao.findAll();
//        System.out.println(list);
        Grade grade = gradeDao.get(1);
        System.out.println(grade);
        gradeDao.delete(1);
        Grade grade1 = gradeDao.get(1);
        System.out.println(grade1);
    }
}
