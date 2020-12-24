package cn.tiakon.learn.java.multithread.chapter08_deadlock;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 用 ThreadMXBean 检测死锁
 * last time   : 2020/11/7 21:57
 *
 * @author tiankai.me@gmail.com on 2020/11/7 21:57.
 */
public class Demo05_ThreadMXBeanDetection {

    public static void main(String[] args) {

        Object o1 = new Object();
        Object o2 = new Object();

        MustDeadLock mustDeadLock01 = new MustDeadLock(o1, o2, 1);
        MustDeadLock mustDeadLock02 = new MustDeadLock(o1, o2, 2);

        Thread thread1 = new Thread(mustDeadLock01);
        Thread thread2 = new Thread(mustDeadLock02);

        thread1.start();
        thread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            for (int i = 0; i < deadlockedThreads.length; i++) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThreads[i]);
                System.out.println("发现死锁:" + threadInfo.getThreadName());
            }
        }

    }
}