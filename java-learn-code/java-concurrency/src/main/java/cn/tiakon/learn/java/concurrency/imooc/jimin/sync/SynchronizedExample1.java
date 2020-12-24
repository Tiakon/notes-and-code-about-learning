package cn.tiakon.learn.java.concurrency.imooc.jimin.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 */
@Slf4j
public class SynchronizedExample1 {

    private void test0() {
        for (int i = 0; i < 10; i++) {
            log.info(">> test1- {}", i);
        }
    }

    // 修饰一个代码块：作用于所调用的对象。
    private void test1(int j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info(">> test1 {} - {}", j, i);
            }
        }
    }

    // 修饰一个方法：作用于所调用的对象。
    private synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info(">> test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        // 模拟多个线程，同时执行一段代码时。
        SynchronizedExample1 example1 = new SynchronizedExample1();
        SynchronizedExample1 example2 = new SynchronizedExample1();

        // 模拟多个线程，同时调用同一对象synchronized的方法
        /*executorService.execute(() -> {
            example1.test2(1);
        });
        executorService.execute(() -> {
            example1.test2(2);
        });*/
        executorService.execute(() -> {
            example2.test1(2);
        });
        executorService.shutdown();
    }

}
