package cn.tiakon.learn.java.multithread.chapter03;

import java.util.concurrent.TimeUnit;

/**
 * 演示join，注意语句输出会变化。
 * 让主线程等待子线程执行完毕后，在执行主线程代码。
 * last time   : 2020/10/8 14:36
 *
 * @author tiankai.me@gmail.com on 2020/10/8 14:36.
 */
public class Demo11_Join {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1，执行完毕...");
        });

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2，执行完毕...");

        });

        t1.start();
        t2.start();
        System.out.println("开始等待子线程执行完毕...");
//         注释掉join代码，再观察输出结果
//        t1.join();
//        t2.join();
        System.out.println("所有子线程执行完毕。");

    }


}
