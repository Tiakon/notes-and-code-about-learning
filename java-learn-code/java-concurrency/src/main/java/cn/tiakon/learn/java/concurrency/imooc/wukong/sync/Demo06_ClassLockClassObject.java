package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 类锁形式2，synchronized修饰类的Class对象。
 * 效果：让synchronized修饰的代码块在多线程执行中串行执行。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo06_ClassLockClassObject implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        Demo06_ClassLockClassObject runnable1 = new Demo06_ClassLockClassObject();
        Demo06_ClassLockClassObject runnable2 = new Demo06_ClassLockClassObject();

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
        // synchronized修饰类的Class对象时，不同对象中的代码在多线程中是串行执行。
        method1();
        // synchronized指定锁对象为this时，不同对象中的代码在多线程中是并行执行。
//        method2();
    }

    public static synchronized void method1() {
        synchronized (Demo06_ClassLockClassObject.class) {
            System.out.println(Thread.currentThread().getName().concat(" 执行 method1..."));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行 method1 结束。"));
        }
    }

    public void method2() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName().concat(" 执行 method2..."));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行 method2 结束。"));
        }
    }
}

