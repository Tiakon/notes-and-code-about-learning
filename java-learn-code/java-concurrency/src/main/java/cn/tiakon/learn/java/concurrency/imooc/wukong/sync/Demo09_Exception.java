package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 方法抛异常后，Jvm帮我们释放锁。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo09_Exception implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        Demo09_Exception runnable1 = new Demo09_Exception();

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable1);
        thread1.start();
        thread2.start();

        while (thread1.isAlive() || thread2.isAlive()) {
        }

        System.out.println(">> finished...");
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method3();
        } else {
            method4();
        }
    }

    public static void method1() {
        synchronized (Demo09_Exception.class) {
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
            throw new RuntimeException("");
//             System.out.println(Thread.currentThread().getName().concat(" 执行 method3 结束。"));
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

