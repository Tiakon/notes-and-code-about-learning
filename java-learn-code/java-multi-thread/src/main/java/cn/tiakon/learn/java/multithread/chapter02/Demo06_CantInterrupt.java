package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 如果 while 里面放try/cache ，会导致异常中断失效
 * last time   : 2020/9/24 14:05
 *
 * @author tiankai.me@gmail.com on 2020/9/24 14:05.
 */
public class Demo06_CantInterrupt {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            int n = 0;
            //  sleep 响应中断后，会清除中断标记。
            while (n <= 30000 && !Thread.currentThread().isInterrupted()) {
                if (n % 100 == 0) {
                    System.out.println(n + " 是100的倍数...");
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                n++;
            }
        });

        thread.start();
        Thread.sleep(5000);
        thread.interrupt();

    }
}
