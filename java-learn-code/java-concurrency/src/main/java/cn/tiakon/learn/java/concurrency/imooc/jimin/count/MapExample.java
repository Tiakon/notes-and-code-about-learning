package cn.tiakon.learn.java.concurrency.imooc.jimin.count;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 模拟200个客户端，5000个请求
 *
 * @author Administrator
 */
@Slf4j
public class MapExample {

    private static int threadNum = 200;
    private static int clientNum = 5000;

    private static Map<Integer, Integer> map = Maps.newHashMap();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadNum);

        for (int index = 0; index < clientNum; index++) {
            final int threadNum = index;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    func(threadNum);
                    semaphore.release();
                } catch (InterruptedException e) {
//                    log.error("{}", e);
                }

            });
        }

        executorService.shutdownNow();
        log.info(">> size: {}", map.size());
    }

    private static void func(int threadNum) {
        map.put(threadNum, threadNum);
    }

}
