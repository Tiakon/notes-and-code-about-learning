package cn.tiakon.learn.java.multithread.chapter06;

import java.util.concurrent.TimeUnit;

/**
 * 第二种线程安全问题：演示多线程死锁
 * <p>
 * last time   : 2020/10/9 7:35
 *
 * @author tiankai.me@gmail.com on 2020/10/9 7:35.
 */
public class Demo03_MultiThreadDead implements Runnable {


    private int flag = 1;
    static Object o1 = new Object();
    static Object o2 = new Object();

    @Override
    public void run() {

        System.out.println("flag: " + flag);

        if (flag == 1) {
            synchronized (o1) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("1");
                }
            }
        }

        if (flag == 2) {
            synchronized (o2) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("2");
                }
            }
        }

    }

    public static void main(String[] args) {

        Demo03_MultiThreadDead d1 = new Demo03_MultiThreadDead();
        Demo03_MultiThreadDead d2 = new Demo03_MultiThreadDead();
        d1.flag = 1;
        d2.flag = 2;
        new Thread(d1).start();
        new Thread(d2).start();
    }

}
