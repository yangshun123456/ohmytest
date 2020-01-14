package agent.dao;

/**
 * @author : yangshun
 * @date : 2020/1/10 9:58
 */
public class RealStar implements Star {
    @Override
    public void confer() {
        System.out.println("confer");
    }

    @Override
    public void signContract() {
        System.out.println("singContract");
    }

    @Override
    public void bookTicket() {
        System.out.println("bookTicket");
    }

    @Override
    public void sing() {
        System.out.println("sing");
    }

    @Override
    public void collectMoney() {
        System.out.println("collectMoney");
    }
}
