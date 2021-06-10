package cn.tiakon.learn.java.multithread.chapter02;

/**
 * run() 内没有 sleep() 或 wait () 时停止线程。
 * <p>
 * last time   : 2020/9/24 12:27
 *
 * @author tiankai.me@gmail.com on 2020/9/24 12:27.
 */
public class Demo03_RightWayStopThreadWithoutSleep implements Runnable {

    @Override
    public void run() {
        int n = 0;

        while (!Thread.currentThread().isInterrupted() && n <= Integer.MAX_VALUE / 2) {
//        while (n <= Integer.MAX_VALUE / 2) {
            if (n % 100000 == 0) {
                System.out.println(n + "是 100000 的倍数。");
            }
            n++;
        }
        System.out.println(">> 任务结束了...");
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Demo03_RightWayStopThreadWithoutSleep());
        thread.start();
        Thread.sleep(1500);
        thread.interrupt();
    }

}
