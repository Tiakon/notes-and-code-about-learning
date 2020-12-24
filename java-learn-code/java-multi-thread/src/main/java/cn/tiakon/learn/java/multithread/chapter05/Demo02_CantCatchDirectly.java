package cn.tiakon.learn.java.multithread.chapter05;

/**
 * 1. 不加 try catch 抛出4个异常，都带线程名字。
 * 2. 加了 try catch 期望捕获到第一个线程的异常，线程 2、3、4 不应该运行，
 * 希望看到打印出 Caught Exception。
 * 3. 执行时发现，根本没有Caught Exception，线程 2、3、4依然运行并且抛出异常。
 * <p>
 * 说明线程的异常不能用传统方法捕获。
 * <p>
 * last time   : 2020/10/8 22:17
 *
 * @author tiankai.me@gmail.com on 2020/10/8 22:17.
 */
public class Demo02_CantCatchDirectly implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        // 加不加 try catch 效果一样。线程的异常不能用传统方法捕获
        try {
            new Thread(new Demo02_CantCatchDirectly(), "MyThread-1").start();
            Thread.sleep(300);
            new Thread(new Demo02_CantCatchDirectly(), "MyThread-2").start();
            Thread.sleep(300);
            new Thread(new Demo02_CantCatchDirectly(), "MyThread-3").start();
            Thread.sleep(300);
            new Thread(new Demo02_CantCatchDirectly(), "MyThread-4").start();
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        throw new RuntimeException("Runtime Exception...");
    }

}
