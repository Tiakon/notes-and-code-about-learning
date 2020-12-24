package cn.tiakon.learn.java.multithread.chapter03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * 1. 为什么要使用生产者和消费者模式？
 * a. 平衡生产与消费的速度，避免一方出现过慢或过快。
 * b. 将生产与消费方进行解耦，提高消息进出的吞吐量。
 * <p>
 * 用 wait / notify 来实现
 * last time   : 2020/10/5 21:40
 *
 * @author tiankai.me@gmail.com on 2020/10/5 21:40.
 */
public class Demo05_ProduceConsumerModel {

    private static Logger logger = LoggerFactory.getLogger(Demo05_ProduceConsumerModel.class);

    public static void main(String[] args) {

        EventStorage eventStorage = new EventStorage(10);

        Produce produce = new Produce(eventStorage);
        Consumer consumer = new Consumer(eventStorage);

        Thread t1 = new Thread(produce);
        Thread t2 = new Thread(consumer);
        t1.start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
        logger.info(t1.getName().concat(" : ").concat(t1.getState().toString()));
        logger.info(t2.getName().concat(" : ").concat(t2.getState().toString()));

    }
}

class EventStorage {

    private static Logger logger = LoggerFactory.getLogger(EventStorage.class);


    private LinkedList<Long> list;

    public int storageSize;

    public EventStorage(int storageSize) {
        this.list = new LinkedList<>();
        this.storageSize = storageSize;
    }

    public synchronized void put(Long timestamp) {
//        System.out.println("put: " + Thread.currentThread().getName());

        while (list.size() == storageSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(timestamp);
        logger.info("添加了一个商品，容量还剩: " + (storageSize - list.size()));
        notify();
    }

    public synchronized void take() {
//        System.out.println("take: " + Thread.currentThread().getName());
        while (list.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("消费了一个商品 " + list.poll() + " ，还剩: " + list.size() + " 个。");
        notify();
    }
}

class Produce implements Runnable {
    private EventStorage eventStorage;

    public Produce(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            eventStorage.put(System.currentTimeMillis());
        }
    }

}

class Consumer implements Runnable {
    private EventStorage eventStorage;

    public Consumer(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            eventStorage.take();
        }
    }
}
