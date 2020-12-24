package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 预期：同一时间获取一个访问许可。
 *
 * @author Administrator
 */
@Slf4j
public class SemaphoreExample1 {

    public static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(20);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    // 获取一个访问许可。
                    semaphore.acquire();
                    test(threadNum);
                    // 释放一个访问许可。
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    public static void test(int count) throws InterruptedException {
        Thread.sleep(1000);
        log.info(">> {}", count);
    }

}
