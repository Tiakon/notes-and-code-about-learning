package cn.tiakon.learn.java.multithread.chapter03;

/**
 * 两个线程交替打印奇偶数 ，用 synchronized 来通信。
 * last time   : 2020/10/6 11:40
 *
 * @author tiankai.me@gmail.com on 2020/10/6 11:40.
 */
public class Demo07_PrintSynOddAndEvenNumber {


    private static Object lock = new Object();
    private static int count = 0;

    public static void main(String[] args) {


        Thread t1 = new Thread(() -> {
            while (count < 100) {
                synchronized (lock) {
                    if ((count & 1) == 0) {
                        System.out.println(Thread.currentThread().getName() + " 偶数: " + count++);
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (count < 100) {
                synchronized (lock) {
                    if ((count & 1) == 1) {
                        System.out.println(Thread.currentThread().getName() + " 奇数: " + count++);
                    }
                }
            }
        });

        t1.start();
        t2.start();

    }

}
