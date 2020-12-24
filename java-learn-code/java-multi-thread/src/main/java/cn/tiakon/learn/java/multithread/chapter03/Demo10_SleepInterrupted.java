package cn.tiakon.learn.java.multithread.chapter03;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 每个1秒钟输出当前时间，被中断、观察。
 * Thread.sleep()
 * TimeUnit.SECONDS.sleep()
 * last time   : 2020/10/8 14:36
 *
 * @author tiankai.me@gmail.com on 2020/10/8 14:36.
 */
public class Demo10_SleepInterrupted {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println(new Date());
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        });

        t1.start();

        Thread.sleep(6500);

        t1.interrupt();
    }


}
