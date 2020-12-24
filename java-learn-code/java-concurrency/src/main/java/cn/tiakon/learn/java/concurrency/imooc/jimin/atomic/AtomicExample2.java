package cn.tiakon.learn.java.concurrency.imooc.jimin.atomic;

import cn.tiakon.learn.java.core.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder在AtomicLong的基础上将单点的更新压力分散到各个节点，
 * 在低并发的时候通过对base的直接更新可以很好的保障和AtomicLong的性能基本保持一致，
 * 而在高并发的时候通过分散提高了性能。
 * 缺点是LongAdder在统计的时候如果有并发更新，
 * 可能导致统计的数据有误差。
 *
 * AtomicLong和LongAdder的区别
 * https://www.cnblogs.com/duanxz/p/3724446.html
 *
 * @author Administrator
 */
@Slf4j
@ThreadSafe
public class AtomicExample2 {


    private static int threadTotal = 200;
    private static int clientTotal = 5000;
    private static LongAdder count = new LongAdder();

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
        log.info(">> count: {}", count);
    }

    private static void add() {
        count.increment();
    }

}
