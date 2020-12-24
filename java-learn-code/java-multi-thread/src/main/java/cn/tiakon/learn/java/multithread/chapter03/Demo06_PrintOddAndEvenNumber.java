package cn.tiakon.learn.java.multithread.chapter03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 两个线程交替打印奇数、偶数。
 * <p>
 * Exception in thread "Thread-0" java.lang.IllegalMonitorStateException
 * at java.lang.Object.notify(Native Method)
 * at cn.tiakon.learn.java.multithread.chapter03.PrintNumber.run(Demo06_PrintOddAndEvenNumber.java:39)
 * at java.lang.Thread.run(Thread.java:745)
 *
 * <p>
 * last time   : 2020/10/6 8:41
 *
 * @author tiankai.me@gmail.com on 2020/10/6 8:41.
 */
public class Demo06_PrintOddAndEvenNumber {

    private static Logger logger = LoggerFactory.getLogger(Demo06_PrintOddAndEvenNumber.class);

    public static void main(String[] args) {

        PrintNumber printNumber = new PrintNumber();

        Thread t1 = new Thread(printNumber);
        Thread t2 = new Thread(printNumber);
        t1.start();
        t2.start();

    }
}

class PrintNumber implements Runnable {

    private int count = 0;

    @Override
    public void run() {
        while (count < 100) {
            this.printNum();
        }

    }

    private synchronized void printNum() {
        System.out.println(Thread.currentThread().getName().concat(" : " + (count++)));
        notify();
        if (count < 100) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


