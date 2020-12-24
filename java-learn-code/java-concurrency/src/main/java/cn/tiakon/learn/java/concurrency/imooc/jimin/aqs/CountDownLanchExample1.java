package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 预期：子线程的任务都执行完成后，再执行主线程后的代码。
 *
 * @author Administrator
 */
@Slf4j
public class CountDownLanchExample1 {

    public static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        log.info(">> finish.");
        executorService.shutdown();

    }

    public static void test(int count) throws InterruptedException {
        Thread.sleep(100);
        log.info(">> {}", count);
    }

}
