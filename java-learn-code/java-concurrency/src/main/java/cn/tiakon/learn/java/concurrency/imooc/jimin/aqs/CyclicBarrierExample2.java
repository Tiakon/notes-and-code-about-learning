package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 预期：一组线程之间相互等待,指定等待时间捕获异常后，再执行后续的代码。
 *
 * @author Administrator
 */
@Slf4j
public class CyclicBarrierExample2 {

    private static CyclicBarrier barrier = new CyclicBarrier(5);

    private static int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                race(threadNum);
            });
        }
        executorService.shutdown();
    }

    public static void race(int index) {
        log.info(">> {} is ready... ", index);
        try {
            barrier.await(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error(">> TimeoutException {}", e);
        }
        log.info(">> {} , continue", index);
    }

}
