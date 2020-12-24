package cn.tiakon.learn.java.multithread.chapter02;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * last time   : 2020/9/24 16:35
 *
 * @author tiankai.me@gmail.com on 2020/9/24 16:35.
 */
public class Demo12_ErrorWayStopThreadVolatileFixed {
    public static void main(String[] args) throws InterruptedException {

        Demo12_ErrorWayStopThreadVolatileFixed body = new Demo12_ErrorWayStopThreadVolatileFixed();

        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(10);
        Produce produce = body.new Produce(blockingQueue);
        Thread produceThread = new Thread(produce);
        produceThread.start();
        Thread.sleep(1000);
        Consumer consumer = body.new Consumer(blockingQueue);
        while (consumer.needMoreNums()) {
            System.out.println(Thread.currentThread().getName() + " " + consumer.storage.take() + " 被消费了...");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要数据了。");
        produceThread.interrupt();
    }

    class Produce implements Runnable {

        BlockingQueue storage;

        public Produce(BlockingQueue storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 0;
            try {
                while (num <= 300000 && !Thread.currentThread().isInterrupted()) {
                    if (num % 100 == 0) {
                        // 队列放满后 put() 方法会阻塞，put() 会响应中断。
                        storage.put(num);
                        System.out.println(Thread.currentThread().getName() + " " + num + " 是被100整除...");
                    }
                    num++;
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

}

