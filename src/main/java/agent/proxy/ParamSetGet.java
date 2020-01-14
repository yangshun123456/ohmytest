package agent.proxy;

/**
 * @author : yangshun
 * @date : 2020/1/10 13:55
 */

import comm.entity.Student;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Student 你自定义的属性
 */
public class ParamSetGet {

    public void isChangeValue(String property, String value, Class model, Student student) {
//
        BeanInfo beanInfo;
        try {
            //throws IntrospectionException
            beanInfo = Introspector.getBeanInfo(student.getClass(), Object.class);
            if (beanInfo != null) {
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor p : propertyDescriptors) {
                    //获得属性名称
                    String name = p.getName();
                    Map<String, Object> map = new HashMap<>();
                    //调用该属性名称对应的getter方法
                    //throws IntrospectionException,InvocationTargetException,IllegalAccessException
                    Object obj = new PropertyDescriptor(p.getName(), Student.class).getReadMethod().invoke(student);
                    //调用该属性名称对应的setter方法
                    new PropertyDescriptor(p.getName(), Student.class).getWriteMethod().invoke(student, obj);
                }
            }
        }catch(IntrospectionException e){
            e.printStackTrace();
        }catch(InvocationTargetException e1){
            e1.printStackTrace();
        }catch(IllegalAccessException e2){
            e2.printStackTrace();
        }

    }
}
