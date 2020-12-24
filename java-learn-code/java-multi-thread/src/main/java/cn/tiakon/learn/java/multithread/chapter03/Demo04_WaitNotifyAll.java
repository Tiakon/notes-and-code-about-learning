package cn.tiakon.learn.java.multithread.chapter03;

/**
 * 3个线程，线程1和线程2首先被阻塞、线程3唤醒它们。
 * wait、notify、notifyAll
 * last time   : 2020/9/26 17:12
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:12.
 */
public class Demo04_WaitNotifyAll {

    private static final Object object = new Object();

    static class Thread1 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " 开始执行...");
                try {
                    // 证明wait释放了锁。
                    System.out.println(Thread.currentThread().getName() + " 执行了wait...");
                    object.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行结束...");
            }
        }
    }

    static class Thread2 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " 开始执行...");
                try {
                    // 证明wait释放了锁。
                    System.out.println(Thread.currentThread().getName() + " 执行了wait...");
                    object.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 执行结束...");
            }
        }
    }

    static class Thread3 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " 开始执行...");
                object.notify();
//                object.notifyAll();
                System.out.println(Thread.currentThread().getName() + " 执行了 notify() ...");
                System.out.println(Thread.currentThread().getName() + " 执行结束...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        Thread3 thread3 = new Thread3();

        thread1.start();
        thread2.start();
        Thread.sleep(200);
        thread3.start();
    }


}
