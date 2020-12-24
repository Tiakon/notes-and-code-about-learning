package cn.tiakon.learn.java.multithread.chapter05;

/**
 * 统一异常捕获。
 * <p>
 * last time   : 2020/10/8 22:17
 *
 * @author tiankai.me@gmail.com on 2020/10/8 22:17.
 */
public class Demo04_UseOwnUncaughtExceptionHandler implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        Thread.setDefaultUncaughtExceptionHandler(new Demo03_MyUncaughtExceptionHandler("Demo04UncaughtExceptionHandler"));
        new Thread(new Demo04_UseOwnUncaughtExceptionHandler(), "MyThread-1").start();
        Thread.sleep(300);
        new Thread(new Demo04_UseOwnUncaughtExceptionHandler(), "MyThread-2").start();
        Thread.sleep(300);
        new Thread(new Demo04_UseOwnUncaughtExceptionHandler(), "MyThread-3").start();
        Thread.sleep(300);
        new Thread(new Demo04_UseOwnUncaughtExceptionHandler(), "MyThread-4").start();
        Thread.sleep(300);

    }

    @Override
    public void run() {
        throw new RuntimeException("Runtime Exception...");
    }

}
