package cn.tiakon.learn.java.multithread.chapter02;

/**
 * start() 与 run() 方法执行的区别。
 *
 * @author Administrator
 */
public class Demo01_StartAndRun {
    public static void main(String[] args) {

        Runnable runnable = new Runnable() {

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
                System.out.println(Thread.currentThread().getName());
            }
        };

        // 在 main 线程中执行 。
        runnable.run();

        // 在 Thread-0 新建的线程中启动。
        new Thread(runnable).start();

    }
}
