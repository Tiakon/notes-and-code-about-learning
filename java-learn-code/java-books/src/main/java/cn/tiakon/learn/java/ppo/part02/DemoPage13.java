package cn.tiakon.learn.java.ppo.part02;

public class DemoPage13 {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new DemoPage12Runnable()).start();
        }
    }
}

/**
 * 通过内部类实现单例类，且天生支持多线程安全。
 * last time   : 2020/7/15 0:10
 *
 * @author tiankai.me@gmail.com on 2020/7/15 0:10.
 */
class StaticSingleton {
    public StaticSingleton() {
        System.out.println("StaticSingleton is create...");
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

}


enum SingletonEnum {



}
