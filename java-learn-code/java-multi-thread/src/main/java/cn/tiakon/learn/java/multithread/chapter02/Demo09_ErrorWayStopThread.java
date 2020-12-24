package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 错误停止线程的方法，用 stop()停止线程，会导致线程运行一半突然停止。
 * last time   : 2020/9/24 15:22
 *
 * @author tiankai.me@gmail.com on 2020/9/24 15:22.
 */
public class Demo09_ErrorWayStopThread {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("-----------"+i+"-----------");
                for (int j = 0; j < 10; j++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(j);
                }

            }
        });
        thread.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.stop();
    }
}
