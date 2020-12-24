package cn.tiakon.learn.java.multithread.chapter03;

import java.util.concurrent.TimeUnit;

/**
 * 先join在mainThread.getState()。
 * 通过debugger看线程join前后状态的对比。
 * last time   : 2020/10/8 14:36
 *
 * @author tiankai.me@gmail.com on 2020/10/8 14:36.
 */
public class Demo12_JoinThreadState {

    private static Thread mainThread = Thread.currentThread();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(mainThread.getName().concat(" " + mainThread.getState()));
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName().concat("线程遇到了中断..."));
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行完毕..."));
        });

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


}
