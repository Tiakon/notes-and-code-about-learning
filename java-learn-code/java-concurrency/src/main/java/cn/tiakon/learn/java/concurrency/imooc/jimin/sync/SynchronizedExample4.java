package cn.tiakon.learn.java.concurrency.imooc.jimin.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 修饰一个类：作用于所有对象。
 *
 * @author Administrator
 */
@Slf4j
public class SynchronizedExample4 {

    private void test1(int j) {
        synchronized (SynchronizedExample4.class) {
            for (int i = 0; i < 10; i++) {
                log.info(">> test1 {} - {}", j, i);
            }
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        SynchronizedExample4 example1 = new SynchronizedExample4();
        SynchronizedExample4 example2 = new SynchronizedExample4();

        // 模拟多个线程，同时执行一段代码时。
        executorService.execute(() -> {
            example1.test1(1);
//            SynchronizedExample2.test2(1);
        });
        executorService.execute(() -> {
            example2.test1(2);
//            SynchronizedExample2.test2(2);
        });

        executorService.shutdown();
    }

}
