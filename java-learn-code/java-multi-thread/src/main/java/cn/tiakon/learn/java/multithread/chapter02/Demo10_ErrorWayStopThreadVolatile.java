package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 错误停止线程的方法2:演示用volatile的局限，part1 看似可行。
 * last time   : 2020/9/24 15:22
 *
 * @author tiankai.me@gmail.com on 2020/9/24 15:22.
 */
public class Demo10_ErrorWayStopThreadVolatile implements Runnable {

    private volatile boolean canceled = false;

    @Override
    public void run() {

        int num = 0;

        while (num <= 100000 && !canceled) {
            try {
                if (num % 100 == 0) {
                    System.out.println(num + " 是100的倍数 ");
                }
                num++;
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Demo10_ErrorWayStopThreadVolatile threadVolatile = new Demo10_ErrorWayStopThreadVolatile();

        Thread thread = new Thread(threadVolatile);
        thread.start();
        Thread.sleep(500);
        threadVolatile.canceled = true;
    }
}
