package cn.tiakon.learn.java.concurrency.imooc.jimin.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExample4 {

    public static void main(String[] args) {

        log.warn("main start...");

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

        Runnable runnable = () -> {
            log.warn("runnable  start...");
            int mark = new Double(Math.random() * 10).intValue();
            log.warn("mark={} , 的任务开始", mark);

            if (mark % 2 == 0) {
                try {
                    int a = 1 / (mark % 2);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(3000);
                        log.warn("mark={} , 的任务结束", mark);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            } else {
                log.warn("mark={} , 的任务结束", mark);
            }
        };

        // 没有使用try catch的话，如果任务的任何执行遇到异常，则将禁止后续执行。
        // 使用try catch的话 ，当前任务执行完成后才会执行下一个任务。
        executorService.scheduleAtFixedRate(runnable, 1, 2, TimeUnit.SECONDS);

        try {
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.warn("main end...");
    }
}
