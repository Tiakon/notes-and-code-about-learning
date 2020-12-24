package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * FutureTask 的使用。
 * 用法方便简洁。
 *
 *
 * @author Administrator
 */
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.info(">> do something in callable...");
            Thread.sleep(5000);
            return "Done";
        });
        new Thread(futureTask).start();
        log.info(">> do something in main...");
        Thread.sleep(1000);
        String result = futureTask.get();
        log.info(">> result: {}", result);
    }
}
