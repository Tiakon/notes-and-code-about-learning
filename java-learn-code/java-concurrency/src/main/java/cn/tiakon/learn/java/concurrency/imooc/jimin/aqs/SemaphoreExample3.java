package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 预期：尝试获取访问许可,获取不到则会放弃访问。
 *
 * @author Administrator
 */
@Slf4j
public class SemaphoreExample3 {

    public static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    if (semaphore.tryAcquire()) {
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
