package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 对象锁：修饰在普通方法上。
 * 效果：让synchronized修饰的方法在多线程执行中串行执行。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo04_ObjectLockMethod implements Runnable {

    Object lock1 = new Object();
    Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        Demo04_ObjectLockMethod runnable = new Demo04_ObjectLockMethod();

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {}

        System.out.println(">> finished...");
    }

    @Override
    public void run() {
        method1();
    }

    public synchronized void method1() {
        System.out.println(Thread.currentThread().getName().concat(" 执行 method1..."));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName().concat(" 执行 method1 结束。"));
    }
}

