package cn.tiakon.learn.java.multithread.chapter02;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 错误停止线程的方法2:演示用volatile的局限，part2 循环逻辑中陷入阻塞时，volatile是无法停止线程的。
 * 此例中，生产者的生产速度很快，消费者消费速度慢，
 * 所以阻塞队列满了以后，生产者会阻塞，等待消费者进一步消费。
 * last time   : 2020/9/24 15:22
 *
 * @author tiankai.me@gmail.com on 2020/9/24 15:22.
 */
public class Demo11_ErrorWayStopThreadVolatileCantStop {
    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(10);
        Produce produce = new Produce(blockingQueue);
        Thread produceThread = new Thread(produce);
        produceThread.start();
        Thread.sleep(1000);
        Consumer consumer = new Consumer(blockingQueue);
        while (consumer.needMoreNums()) {
            System.out.println(Thread.currentThread().getName() + " " + consumer.storage.take() + " 被消费了...");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要数据了。");
        produce.canceled = true;
        System.out.println(produce.canceled);
    }
}

class Produce implements Runnable {

    public volatile boolean canceled = false;

    BlockingQueue storage;

    public Produce(BlockingQueue storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (num <= 300000 && !canceled) {
                if (num % 100 == 0) {
                    // 队列放满后 put() 方法会阻塞导致canseled失效。
                    storage.put(num);
                    System.out.println(Thread.currentThread().getName() + " " + num + " 是被100整除...");
                }
                num++;
//                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止...");
        }
    }
}

class Consumer {

    BlockingQueue storage;

    public Consumer(BlockingQueue storage) {
        this.storage = storage;
    }

    public boolean needMoreNums() {
        if (Math.random() > 0.95) {
            return false;
        }
        return true;
    }
}