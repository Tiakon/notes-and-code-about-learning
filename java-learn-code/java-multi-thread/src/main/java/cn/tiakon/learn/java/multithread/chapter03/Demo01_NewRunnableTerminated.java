package cn.tiakon.learn.java.multithread.chapter03;

/**
 * 展示线程的New Runbable Terminated 状态。
 * <p>
 * 即使是正在运行，也是Runbale状态，而不是 Running。
 * <p>
 * last time   : 2020/9/24 17:41
 *
 * @author tiankai.me@gmail.com on 2020/9/24 17:41.
 */
public class Demo01_NewRunnableTerminated {
    public static void main(String[] args) {
        try {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });

            System.out.println(thread.getState());

            thread.start();
            System.out.println(thread.getState());

            Thread.sleep(1000);
            System.out.println(thread.getState());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
