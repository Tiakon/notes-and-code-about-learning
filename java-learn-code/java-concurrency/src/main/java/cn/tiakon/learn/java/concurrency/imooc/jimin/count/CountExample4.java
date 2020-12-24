package cn.tiakon.learn.java.concurrency.imooc.jimin.count;

import cn.tiakon.learn.java.core.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * 使用 synchronized 实现线程安全。
 *
 * @author Administrator
 */
@Slf4j
@ThreadSafe
public class CountExample4 {


    private static int threadTotal = 200;
    private static int clientTotal = 5000;
    private static long count = 0;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int index = 0; index < clientTotal; index++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
//                    logger.error("{}", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdownNow();
        log.info(">> count: {}", count);

    }

    private synchronized static void add() {
        count++;
    }

}
