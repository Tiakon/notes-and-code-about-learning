package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 1. 阻塞时，收到中断信号会抛出 InterruptedException: sleep interrupted 异常。
 * 2. 每次循环都需要阻塞(sleep、wait)时，可以省去 !Thread.currentThread().isInterrupted() 线程是否中断的判断。
 * <p>
 * last time   : 2020/9/24 12:54
 *
 * @author tiankai.me@gmail.com on 2020/9/24 12:54.
 */
public class Demo05_RightWayStopThreadWithSleepEveryLoop {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                int n = 0;
                while (n <= 30000) {
                    if (n % 100 == 0) {
                        System.out.println(n + " 是100的倍数...");
                    }
                    Thread.sleep(10);
                    n++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }

}
