package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 使用同步锁。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo07_RightWayDisappearRquests implements Runnable {

    static int i = 0;

    public static void main(String[] args) throws InterruptedException {

        Demo07_RightWayDisappearRquests runnable = new Demo07_RightWayDisappearRquests();

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }

    @Override
    public void run() {
//        calcu1();
//        calcu2();
//        calcu3();
        calcu4();
    }

    public synchronized void calcu1() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }

    public void calcu2() {
        synchronized (this) {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        }
    }

    public static synchronized void calcu3() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }

    public void calcu4() {
        synchronized (Demo07_RightWayDisappearRquests.class) {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        }
    }
}

