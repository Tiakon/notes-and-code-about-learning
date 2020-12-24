package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 类锁形式1，作用在静态方法上。
 * 效果：让synchronized修饰的方法在多线程执行中串行执行。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo05_ClassLockStaticMethod implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        Demo05_ClassLockStaticMethod runnable1 = new Demo05_ClassLockStaticMethod();
        Demo05_ClassLockStaticMethod runnable2 = new Demo05_ClassLockStaticMethod();

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
        }

        System.out.println(">> finished...");
    }

    @Override
    public void run() {
        // synchronized修饰静态方法，不同对象中的代码在多线程中串行执行。
        method1();
        // synchronized修饰普通方法，不同对象中的代码在多线程中并行执行。
//        method2();
    }

    public static synchronized void method1() {
        System.out.println(Thread.currentThread().getName().concat(" 执行 method1..."));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName().concat(" 执行 method1 结束。"));
    }

    public synchronized void method2() {
        System.out.println(Thread.currentThread().getName().concat(" 执行 method2..."));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName().concat(" 执行 method2 结束。"));
    }
}

