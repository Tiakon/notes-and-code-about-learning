package cn.tiakon.learn.java.concurrency.imooc.jimin.count;

import cn.tiakon.learn.java.core.annotation.NonThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * volatile 不具有原子性操作，
 * <p>
 * volatile 的作用：
 * 1. 状态标示量
 * 2. 双重检测
 * <p>
 * 模拟200个客户端，5000个请求
 *
 * @author Administrator
 */
@Slf4j
@NonThreadSafe
public class CountExample5 {

    private static int threadTotal = 200;
    private static int clientTotal = 5000;

    private static volatile long count = 0;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 表示同一时间内最多只允许200个线程执行 acquire()和release()之间的代码。
        final Semaphore semaphore = new Semaphore(threadTotal);

        // 解决子线程还在执行，主线程已经执行结束的问题。
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int index = 0; index < clientTotal; index++) {
            executorService.execute(() -> {
                try {
                    // 方法acquire(n) 的功能是每调用1次此方法，就消耗掉n个许可。
                    semaphore.acquire();
                    add();
                    // 方法release(n) 的功能是每调用1次此方法，就动态添加n个许可。
                    semaphore.release();
                } catch (InterruptedException e) {
//                    log.error("  {}", e.getMessage());
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdownNow();
        log.info(">> count: {}", count);
    }

    private static void add() {
        count++;
    }

}
