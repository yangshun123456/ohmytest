package agent.proxy;

import agent.dao.Animal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author : yangshun
 * @date : 2020/1/10 10:27
 */
public class AnimalProxy implements InvocationHandler {
    private Animal animal=null;
    public AnimalProxy(Animal animal){
        this.animal=animal;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object=null;
        System.out.println("-----------------");
        object=method.invoke(animal,args);
        return object;
    }
}
