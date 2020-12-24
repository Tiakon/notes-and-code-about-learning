package cn.tiakon.learn.java.multithread.chapter03;

/**
 * 证明wait只释放当前的那把锁。
 * <p>
 * last time   : 2020/9/26 17:12
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:12.
 */
public class Demo04_WaitOwnMonitor {

    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    static class Thread3 extends Thread {

        @Override
        public void run() {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + " 开始执行...");
                resourceB.notify();
//                object.notifyAll();
                System.out.println(Thread.currentThread().getName() + " 执行了 notify() ...");
                System.out.println(Thread.currentThread().getName() + " 执行结束...");
            }
        }
    }

    public static void main(String[] args) {

        try {
            Thread thread1 = new Thread(() -> {
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread().getName() + " get resourceA lock...");
                    try {
                        synchronized (resourceB) {
                            System.out.println(Thread.currentThread().getName() + " get resourceB lock...");
                            System.out.println(Thread.currentThread().getName() + " excute wait()...");
                            resourceA.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread().getName() + " get resourceA lock...");
                    System.out.println(Thread.currentThread().getName() + " tries resourceB lock...");
                    synchronized (resourceB) {
                        System.out.println(Thread.currentThread().getName() + " get resourceB lock...");
                    }
                }
            });

            thread1.start();
            Thread.sleep(1000);
            thread2.start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }

    }

}
