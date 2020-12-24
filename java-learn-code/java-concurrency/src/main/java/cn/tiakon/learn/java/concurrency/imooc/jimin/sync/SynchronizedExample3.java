package cn.tiakon.learn.java.concurrency.imooc.jimin.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 */
@Slf4j
public class SynchronizedExample3 {

    // 修饰一个静态方法：作用于所有对象。
    private static synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info(">> test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 模拟多个线程，同时执行一段代码时。
        executorService.execute(() -> {
            SynchronizedExample3.test2(1);
        });
        executorService.execute(() -> {
            SynchronizedExample3.test2(2);
        });

        executorService.shutdown();
    }
}
