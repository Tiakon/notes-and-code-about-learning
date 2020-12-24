package cn.tiakon.learn.java.multithread.chapter07_jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 不适用的场景
 * 1. a++
 * 2.赋值操作中会依赖上一次的状态。
 * volatile 适用的场景
 * 仅有赋值操作。因为赋值操作具有原子性（操作不依赖上一次的状态），vlotile又保证了可见性，所以是线程安全的。
 * last time   : 2020/10/28 0:20
 *
 * @author tiankai.me@gmail.com on 2020/10/28 0:20.
 */
public class Demo05_NoVolatile2 {

    public static void main(String[] args) {

        RunableStyple3 runableStyple = new RunableStyple3();

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
        System.out.println(runableStyple.done);
        System.out.println(runableStyple.atomicInteger.get());
    }

}

class RunableStyple3 implements Runnable {
    volatile boolean done = false;
    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            filpDone();
            atomicInteger.incrementAndGet();
        }
    }

    private void filpDone() {
        done = !done;
    }
}
