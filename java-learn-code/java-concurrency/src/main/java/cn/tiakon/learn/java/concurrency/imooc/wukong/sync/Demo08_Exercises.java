package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 类锁形式2，synchronized修饰类的Class对象。
 * 效果：让synchronized修饰的代码块在多线程执行中串行执行。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo08_Exercises implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        Demo08_Exercises runnable1 = new Demo08_Exercises();
        Demo08_Exercises runnable2 = new Demo08_Exercises();

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

        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method4();
        }
//        method3();
    }

    public static void method1() {
        synchronized (Demo08_Exercises.class) {
            System.out.println(Thread.currentThread().getName().concat(" 执行 method1..."));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行 method1 结束。"));
        }
    }

    public static synchronized void method2() {
        System.out.println(Thread.currentThread().getName().concat(" 执行 method2..."));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName().concat(" 执行 method2 结束。"));
    }

    public void method3() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName().concat(" 执行 method3..."));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行 method3 结束。"));
        }
    }

    public synchronized void method4() {

        System.out.println(Thread.currentThread().getName().concat(" 执行 method4..."));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName().concat(" 执行 method4 结束。"));
    }

    public void method5() {
        System.out.println(Thread.currentThread().getName().concat(" 执行 method5..."));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName().concat(" 执行 method5 结束。"));
    }
}

