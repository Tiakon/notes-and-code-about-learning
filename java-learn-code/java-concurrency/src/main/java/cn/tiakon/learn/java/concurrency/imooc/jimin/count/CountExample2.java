package cn.tiakon.learn.java.concurrency.imooc.jimin.count;

import cn.tiakon.learn.java.core.annotation.NonThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Java并发编程：CountDownLatch、CyclicBarrier和Semaphore
 * https://www.cnblogs.com/dolphin0520/p/3920397.html
 * <p>
 * 并发编程 Semaphore的使用和详解
 * https://www.cnblogs.com/wujiaofen/p/11356436.html
 * <p>
 * 模拟200个客户端，5000个请求
 *
 * @author Administrator
 */
@Slf4j
@NonThreadSafe
public class CountExample2 {


    private static int threadTotal = 200;
    private static int clientTotal = 5000;
    private static long count = 0;

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
                    log.error("  {}", e.getMessage());
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
