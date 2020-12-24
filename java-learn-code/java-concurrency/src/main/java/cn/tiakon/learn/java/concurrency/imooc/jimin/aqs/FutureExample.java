package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Future的使用。
 * @author Administrator
 */
@Slf4j
public class FutureExample {


    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info(">> do something in callable...");
            Thread.sleep(5000);
            return "Done";
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new MyCallable());
        log.info(">> do something in main...");
        Thread.sleep(1000);

        // 任务没有完成时，调用 get() 会阻塞，直到任务完成。
        String result = future.get();
        log.info(">> result: {}", result);
    }


}
