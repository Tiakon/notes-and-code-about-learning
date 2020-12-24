package cn.tiakon.learn.java.concurrency.imooc.jimin.count;

import cn.tiakon.learn.java.core.annotation.NonThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//@Slf4j

/**
 * 模拟200个客户端，5000个请求
 *
 * @author Administrator
 */
@NonThreadSafe
public class CountExample1 {

    private static Logger logger = LoggerFactory.getLogger(CountExample1.class.getName());

    private static int threadTotal = 200;
    private static int clientTotal = 5000;
    private static long count = 0;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);

        for (int index = 0; index < clientTotal; index++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
//                    logger.error("{}", e);
                }
            });
        }

        executorService.shutdownNow();
        logger.info(">> count: {}", count);

    }

    private static void add() {
        count++;
    }

}
