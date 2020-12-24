package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 阻塞时，收到中断信号会抛出 InterruptedException: sleep interrupted 异常。
 * <p>
 * last time   : 2020/9/24 12:54
 *
 * @author tiankai.me@gmail.com on 2020/9/24 12:54.
 */
public class Demo04_RightWayStopThreadWithSleep {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                int n = 0;
                while (n <= 300 && !Thread.currentThread().isInterrupted()) {
                    if (n % 100 == 0) {
                        System.out.println(n + " 是100的倍数...");
                    }
                    n++;
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }

}
