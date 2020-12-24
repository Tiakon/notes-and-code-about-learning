package cn.tiakon.learn.java.multithread.chapter03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sleep不释放锁
 * last time   : 2020/10/6 17:34
 *
 * @author tiankai.me@gmail.com on 2020/10/6 17:34.
 */
public class Demo09_SleepLock implements Runnable {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Demo09_SleepLock sleepLock = new Demo09_SleepLock();
        new Thread(sleepLock).start();
        new Thread(sleepLock).start();
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName().concat(" : 获得 monitor 锁..."));
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName().concat(" : sleep 结束..."));
    }

}
