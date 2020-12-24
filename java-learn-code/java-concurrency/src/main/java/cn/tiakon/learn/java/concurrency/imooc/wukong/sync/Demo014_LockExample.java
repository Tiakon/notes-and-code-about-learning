package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock的简单用法
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo014_LockExample {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        lock.lock();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        // 尝试获取锁
        lock.tryLock(1000, TimeUnit.SECONDS);

    }

}

