package cn.tiakon.learn.java.concurrency.imooc.jimin.count;

import cn.tiakon.learn.java.core.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用 AtomicInteger 实现线程安全。
 *
 * 模拟200个客户端，5000个请求
 *
 * @author Administrator
 */
@Slf4j
@ThreadSafe
public class CountExample3 {


    private static int threadTotal = 200;
    private static int clientTotal = 5000;
    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(5000);

        for (int index = 0; index < clientTotal; index++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
//                    log.error("  {}", e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdownNow();
        log.info(">> count: {}", count.get());
    }

    private static void add() {
        count.incrementAndGet();
//        count.getAndIncrement();
    }

}
