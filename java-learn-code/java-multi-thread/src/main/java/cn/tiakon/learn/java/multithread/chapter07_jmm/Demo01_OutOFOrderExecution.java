package cn.tiakon.learn.java.multithread.chapter07_jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 演示重排序现象
 * 直到达到某个条件才停止，测试小概率事件。
 * last time   : 2020/10/13 17:47
 *
 * @author tiankai.me@gmail.com on 2020/10/13 17:47.
 */
public class Demo01_OutOFOrderExecution {

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch countDownLatch = new CountDownLatch(1);

            Thread one = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            });
            Thread two = new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = 1;
                y = a;
            });

            two.start();
            one.start();

            countDownLatch.countDown();

            one.join();
            two.join();

            String result = "第 " + i + " 次 " + " (" + x + "," + y + ")";
            if (x == 0 && y == 0) {
                System.out.println(result);
                break;
            }/* else {
                System.out.println(result);
            }*/

            //1. one 先执行  x=0,y=1
            //2. twn 先执行  x=1,y=0
            //3. one twn 同时执行     x=1,y=1
            //4. y=a;a=1;x=b;b=1;   发生了指令重排序 => x=0,y=0
        }
    }
}
