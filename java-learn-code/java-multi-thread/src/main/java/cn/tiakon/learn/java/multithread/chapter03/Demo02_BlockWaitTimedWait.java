package cn.tiakon.learn.java.multithread.chapter03;

/**
 * 展示线程的 Blocked、Waiting Timed、Waiting  三种状态。
 * Blocked 执行的方法或代码 加锁后，会进入此状态。
 * <p>
 * 即使是正在运行，也是Runbale状态，而不是 Running。
 * <p>
 * last time   : 2020/9/24 17:41
 *
 * @author tiankai.me@gmail.com on 2020/9/24 17:41.
 */
public class Demo02_BlockWaitTimedWait {
    public static void main(String[] args) {

        try {
            RunnableStyle runnableStyle = new RunnableStyle();

            Thread thread1 = new Thread(runnableStyle);
            thread1.start();

            Thread thread2 = new Thread(runnableStyle);
            thread2.start();
            Thread.sleep(0);
            // TIMED_WAITING , 执行了 Thread.sleep(1000);
            System.out.println(thread1.getState());

            // BLOCKED , 想拿到 sync() 的锁而拿不到。
            System.out.println(thread2.getState());

            Thread.sleep(1300);
            // WAITING , 执行了 wait(); 方法
            System.out.println(thread1.getState());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class RunnableStyle implements Runnable {

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        sync();
    }

    private synchronized void sync() {
        try {
            Thread.sleep(1000);
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}