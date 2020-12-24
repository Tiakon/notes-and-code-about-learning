package cn.tiakon.learn.java.multithread.chapter07_jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 不适用的场景
 * 1. a++
 * last time   : 2020/10/28 0:20
 *
 * @author tiankai.me@gmail.com on 2020/10/28 0:20.
 */
public class Demo03_NoVolatile {

    public static void main(String[] args) {

        RunableStyple runableStyple = new RunableStyple();

        Thread thread1 = new Thread(runableStyple);
        Thread thread2 = new Thread(runableStyple);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(runableStyple.i);
        System.out.println(runableStyple.atomicInteger.get());
    }

}

class RunableStyple implements Runnable {
    volatile int i;
    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void run() {

        for (int j = 0; j < 10000; j++) {
            i++;
            atomicInteger.incrementAndGet();
        }
    }
}
