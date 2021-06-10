package cn.tiakon.learn.java.concurrency.imooc.jimin.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class TimerExample {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                log.warn("timer run");
            }
        };

//        任务 - 要安排的任务。
//        delay – 执行任务前的延迟（以毫秒为单位）。
//        period – 连续任务执行之间的时间（以毫秒为单位）
        timer.schedule(timerTask, 1000, 5 * 1000);
    }
}
