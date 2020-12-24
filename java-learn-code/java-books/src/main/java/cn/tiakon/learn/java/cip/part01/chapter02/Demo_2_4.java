package cn.tiakon.learn.java.cip.part01.chapter02;

import cn.tiakon.learn.java.core.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo_2_4 {
}

/**
 * 使用 AtomicInteger 实现线程安全。
 * <p>
 * 模拟200个客户端，5000个请求
 *
 * @author Administrator
 */
@Slf4j
@ThreadSafe
class CountingFactorizer {


    private AtomicInteger count = new AtomicInteger(0);

    private void add() {
        count.incrementAndGet();
//        count.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {

        int threadTotal = 200;
        int clientTotal = 5000;

        ExecutorService executorService = Executors.newCachedThreadPool();

        CountingFactorizer countingFactorizer = new CountingFactorizer();

        final Semaphore semaphore = new Semaphore(threadTotal);

        // 记录执行次数
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int index = 0; index < clientTotal; index++) {
            executorService.execute(() -> {
                try {
                    semaphore.tryAcquire();
                    countingFactorizer.add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("  {}", e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        executorService.shutdownNow();
        countDownLatch.await();

        log.info(">> count: {}", countingFactorizer.count);
    }


}
