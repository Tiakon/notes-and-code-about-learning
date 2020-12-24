package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * synchronized 修饰自己指定的锁对象，让代码块在多线程执行中串行执行。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo03_ObjectLockMyselfObjectLock implements Runnable {

    Object lock1 = new Object();
    Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        Demo03_ObjectLockMyselfObjectLock runnable = new Demo03_ObjectLockMyselfObjectLock();

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
        }

        System.out.println(">> finished...");
    }

    @Override
    public void run() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName().concat(" 执行 lock1..."));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行 lock1 结束。"));
        }
        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName().concat(" 执行 lock2..."));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行 lock2 结束。"));
        }

    }
}

