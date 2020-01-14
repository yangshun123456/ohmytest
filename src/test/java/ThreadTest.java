import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangshun
 * @date : 2020/1/9 9:44
 */
public class ThreadTest {
    private static List<Integer> food=new ArrayList<>();

    public static void main(String[] args) {
        for(int j=0;j<3;j++) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (food) {
                        if (food.size() <= 10) {
                            food.add(1);
                            System.out.println("做了一个,还剩"+food.size());
                            if (food.size() > 10) {
                                food.notify();
                            }
                        } else {
                            try {
                                System.out.println("做满了");
                                food.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (food){
                    if(food.size()>0){
                        food.remove(0);
                        System.out.println("吃了一个,还剩"+food.size());
                        if(food.size()<1){
                            food.notify();
                        }
                    }else{
                        try {
                            System.out.println("吃完了");
                            food.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


        while (true) {

        }
    }
}
