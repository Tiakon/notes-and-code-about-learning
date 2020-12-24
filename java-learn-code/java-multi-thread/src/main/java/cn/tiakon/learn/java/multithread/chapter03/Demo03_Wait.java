package cn.tiakon.learn.java.multithread.chapter03;

/**
 * 展示wait和notify的基本用法
 * 1. 研究代码顺序。
 * 2. 证明wait释放锁。
 * last time   : 2020/9/26 17:12
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:12.
 */
public class Demo03_Wait {

    private static Object object = new Object();

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
                object.notify();
                System.out.println(Thread.currentThread().getName() + " 执行了 notify() ...");
                System.out.println(Thread.currentThread().getName() + " 执行结束...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        thread1.start();
        Thread.sleep(200);
        Thread2 thread2 = new Thread2();
        thread2.start();
    }

}
