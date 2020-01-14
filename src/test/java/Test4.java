import agent.dao.Animal;
import agent.dao.Dog;
import agent.dao.RealStar;
import agent.dao.Star;
import agent.proxy.AnimalProxy;
import agent.proxy.StarHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author : yangshun
 * @date : 2020/1/10 10:03
 */
public class Test4 {
    @Test
    public void test(){
        Star realStar = new RealStar();
        StarHandler handler = new StarHandler(realStar);

        //通过Proxy生成代理类对象并指定对应的处理器对象
        Star proxyStar = (Star) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{Star.class}, handler);

        proxyStar.sing();
        proxyStar.bookTicket();
    }
    @Test
    public void test1(){
        Animal animal=new Dog();
        AnimalProxy animalProxy=new AnimalProxy(animal);

        Animal animal1= (Animal) Proxy.newProxyInstance(Animal.class.getClassLoader(),Dog.class.getInterfaces(),animalProxy);
        animal1.eat();
    }
}
