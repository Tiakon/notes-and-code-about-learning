package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * synchronized 修饰 锁对象为 this ，让synchronized修饰的代码块在多线程执行中串行执行。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo02_ObjectLockThis implements Runnable {
    public static void main(String[] args) throws InterruptedException {

        Demo02_ObjectLockThis runnable = new Demo02_ObjectLockThis();

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
        synchronized (this) {
            System.out.println(Thread.currentThread().getName().concat(" 执行 run()..."));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" 执行结束。"));
        }
    }
}

