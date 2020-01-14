package agent.proxy;

import agent.dao.Star;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : yangshun
 * @date : 2020/1/10 10:01
 */
public class StarHandler implements InvocationHandler {
    private Star realStar;

    public StarHandler(Star realStar) {
        super();
        this.realStar = realStar;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //返回值
        Object object = null;

        System.out.println("真正的方法执行前！");
        System.out.println("面谈，签合同，预付款，订机票");

//        if(isMethod(method.getName(),"sing")){
//            object = method.invoke(realStar, args);
//        }else if(isMethod(method.getName(),"bookTicket")){
//            object=method.invoke(this.realStar,args);
//        }
        object = method.invoke(realStar,args);

        System.out.println("真正的方法执行后！");
        System.out.println("收尾款");
        return object;
    }

    public boolean isMethod(String method,String other){
        if(other.equals(method)){
            return true;
        }
        return false;
    }
}
