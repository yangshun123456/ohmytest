import com.dao.DogDao;
import com.entity.Dog;
import com.service.DogService;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangshun
 * @date : 2020/1/8 9:33
 */
public class Test {
    private ApplicationContext applicationContext=null;

    @Before
    public void init(){
        applicationContext=new ClassPathXmlApplicationContext("application.xml");
    }

    @org.junit.Test
    public void test1(){
        DogDao dogDao= (DogDao) applicationContext.getBean("dogDao");
        dogDao.letSing(new Dog());
    }

    @org.junit.Test
    public void test2(){
        DogService dogService= (DogService) applicationContext.getBean("dogService");
        List<Dog> list=new ArrayList<Dog>();
        for(int i=0;i<5;i++){
            Dog dog=new Dog();
            dog.setColor("白色");
            dog.setLeg(i);
            dog.setName("狗"+i);
            list.add(dog);
        }
        List<Dog> all = dogService.findAll(list);
        for (Dog dog : all) {
            System.out.println(dog);
        }
    }
}
