package cn.tiakon.learn.java.concurrency.imooc.jimin.atomic;

import cn.tiakon.learn.java.core.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicLong的原理是依靠底层的cas来保障原子性的更新数据，在要添加或者减少的时候，
 * 会使用死循环不断地cas到特定的值，从而达到更新数据的目的。
 *
 * @author Administrator
 */
@Slf4j
@ThreadSafe
public class AtomicExample1 {
    
    private static int threadTotal = 200;
    private static int clientTotal = 5000;
    private static AtomicLong count = new AtomicLong(0);

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
