package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 预期：在指定时间内，尝试获取访问许可,获取不到则会放弃访问。
 *
 * @author Administrator
 */
@Slf4j
public class SemaphoreExample4 {

    public static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    // 可同时获取多个访问
                    if (semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)) {
                        test(threadNum);
                        // 释放一个访问许可。
                        semaphore.release();
                    }else{
                        log.info(">> 放弃访问许可...");
                    }
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
