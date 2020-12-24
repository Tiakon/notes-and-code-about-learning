package cn.tiakon.learn.java.concurrency.imooc.jimin.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 修饰一个方法：作用于所调用的对象。
 *
 * @author Administrator
 */
@Slf4j
public class SynchronizedExample2 {

    private synchronized void test1(int j) {
        for (int i = 0; i < 10; i++) {
            log.info(">> test1 {} - {}", j, i);
        }
    }

    private void test2(int j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info(">> test1 {} - {}", j, i);
            }
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        SynchronizedExample2 example1 = new SynchronizedExample2();
        SynchronizedExample2 example2 = new SynchronizedExample2();

        // 模拟多个线程，同时执行一段代码时。

        executorService.execute(() -> {
            example2.test1(3);
        });

        executorService.execute(() -> {
            example1.test1(1);
        });

        executorService.execute(() -> {
            example1.test1(2);
        });

        executorService.shutdown();
    }
}
