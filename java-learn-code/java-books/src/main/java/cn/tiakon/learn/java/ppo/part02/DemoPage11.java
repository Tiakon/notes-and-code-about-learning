package cn.tiakon.learn.java.ppo.part02;

public class DemoPage11 {
}

/**
 * 单例模式
 * 缺点：
 * 1. 创建单例的过程可能较慢。
 * 2. 且没有实现延迟加载。
 * <p>
 * last time   : 2020/7/14 23:06
 *
 * @author tiankai.me@gmail.com on 2020/7/14 23:06.
 */
class Singleton01 {
    private Singleton01() {
        System.out.println("Singleton01 is create...");
    }

    private static Singleton01 instance = new Singleton01();

    public static Singleton01 getInstance() {
        return instance;
    }

    public static void createString() {
        System.out.println("Cretae string in Singleton");
    }


    public static void main(String[] args) throws InterruptedException {
        Singleton01.createString();

        Thread.sleep(10000);

        Singleton01 instance = Singleton01.getInstance();
        // cn.tiakon.java.ppo.part02.Singleton01@6ce253f1
        System.out.println(instance);
        Thread.sleep(10000);

        instance = Singleton01.getInstance();
        // cn.tiakon.java.ppo.part02.Singleton01@6ce253f1
        System.out.println(instance);

        Thread.sleep(10000);
        instance = Singleton01.getInstance();
        // cn.tiakon.java.ppo.part02.Singleton01@6ce253f1
        System.out.println(instance);

        Thread.sleep(10000);
        instance = Singleton01.getInstance();
        // cn.tiakon.java.ppo.part02.Singleton01@6ce253f1
        System.out.println(instance);
    }
}


