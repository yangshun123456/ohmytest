package agent.dao;

/**
 * @author : yangshun
 * @date : 2020/1/10 10:25
 */
public class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("正在吃饭");
    }

    @Override
    public void run() {
        System.out.println("正在跑步");
    }

    @Override
    public void play() {
        System.out.println("正在玩");
    }

}
