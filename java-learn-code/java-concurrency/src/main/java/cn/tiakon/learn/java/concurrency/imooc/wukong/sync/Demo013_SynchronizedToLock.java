package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 *
 * synchronized & lock的等价写法
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo013_SynchronizedToLock {

    private Lock lock = new ReentrantLock();


    public static void main(String[] args) throws InterruptedException {
        Demo013_SynchronizedToLock demo013_synchronizedToLock = new Demo013_SynchronizedToLock();

        demo013_synchronizedToLock.method1();
        demo013_synchronizedToLock.method2();
    }

    public synchronized void method1() {
        System.out.println(">> 开始...");
        System.out.println(">> synchronized形式的锁");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(">> 结束...");

    }

    public void method2() {
        System.out.println(">> 开始...");
        lock.lock();
        try {
            System.out.println(">> lock形式的锁");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println(">> 结束...");
    }
}

