package cn.tiakon.learn.java.multithread.chapter07_jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile 不适用的场景
 * 1. a++
 * volatile 适用的场景
 * 仅有赋值操作。因为赋值操作具有原子性，vlotile又保证了可见性，所以是线程安全的。
 * last time   : 2020/10/28 0:20
 *
 * @author tiankai.me@gmail.com on 2020/10/28 0:20.
 */
public class Demo04_UseVolatile {

    public static void main(String[] args) {

        RunableStyple2 runableStyple = new RunableStyple2();

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

class RunableStyple2 implements Runnable {
    volatile boolean done = false;
    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            setDone();
            atomicInteger.incrementAndGet();
        }
    }

    private void setDone() {
        done = true;
    }
}
