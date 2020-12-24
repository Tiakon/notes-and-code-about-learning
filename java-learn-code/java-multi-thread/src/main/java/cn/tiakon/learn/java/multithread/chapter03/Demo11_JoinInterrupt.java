package cn.tiakon.learn.java.multithread.chapter03;

import java.util.concurrent.TimeUnit;

/**
 * 演示 join 期间被中断的效果。
 * last time   : 2020/10/8 14:36
 *
 * @author tiankai.me@gmail.com on 2020/10/8 14:36.
 */
public class Demo11_JoinInterrupt {

    private static Thread mainThread = Thread.currentThread();

    public static void main(String[] args) {

        Thread t1 = new Thread(Demo11_JoinInterrupt::exe);

        t1.start();

        System.out.println("开始等待子线程执行完毕...");

        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName().concat("线程遇到了中断..."));
            e.printStackTrace();
            t1.interrupt();
        }

        System.out.println("所有子线程执行完毕。");

    }

    private static void exe() {
        try {
            mainThread.interrupt();
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName().concat("线程遇到了中断..."));
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName().concat(" 执行完毕..."));
    }

}
