package cn.tiakon.learn.java.ppo.part02;

public class DemoPage12 {
}

/**
 * 单例模式(添加了synchronized)
 * 为了使用延迟加载引入的同步关键字反而降低了系统性能。
 *
 * <p>
 * last time   : 2020/7/14 23:06
 *
 * @author tiankai.me@gmail.com on 2020/7/14 23:06.
 */
class LazySingleton {
    private LazySingleton() {
        System.out.println("LazySingleton is create...");
    }

    private static LazySingleton instance = null;

    /**
     * 不加 synchronized 多线程下会引发竞态条件。
     * last time   : 2020/7/15 0:06
     *
     * @author tiankai.me@gmail.com on 2020/7/15 0:06.
     */
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    public static void createString() {
        System.out.println("Cretae string in Singleton");
    }

    public static void main(String[] args) throws InterruptedException {

        LazySingleton.createString();

        Thread.sleep(10000);

        LazySingleton instance = LazySingleton.getInstance();
        // cn.tiakon.java.ppo.part02.Singleton01@6ce253f1
        System.out.println(instance);
        Thread.sleep(10000);

        instance = LazySingleton.getInstance();
        // cn.tiakon.java.ppo.part02.Singleton01@6ce253f1
        System.out.println(instance);

//        for (int i = 0; i < 5; i++) {
//            new Thread(new DemoPage12Runnable()).start();
//        }

    }
}

class DemoPage12Runnable implements Runnable {

    private long currentTimeMillis = System.currentTimeMillis();

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            /**
             * spend:6
             * spend:7
             * spend:8
             * spend:8
             * spend:8
             * */
//            Singleton01.getInstance();
            /**
             * spend:16
             * spend:34
             * spend:36
             * spend:40
             * spend:40
             * */
//            LazySingleton.getInstance();

            /**
             * spend:11
             * spend:11
             * spend:13
             * spend:13
             * spend:13
             * */
            StaticSingleton.getInstance();
        }
        System.out.println("spend:" + (System.currentTimeMillis() - currentTimeMillis));
    }
}
