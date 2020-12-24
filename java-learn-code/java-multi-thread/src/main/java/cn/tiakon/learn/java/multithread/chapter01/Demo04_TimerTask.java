package cn.tiakon.learn.java.multithread.chapter01;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用定时器创建线程。
 *
 * @author Administrator
 */
public class Demo04_TimerTask {
    public static void main(String[] args) {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };

        // 启动 5 秒后，每隔1秒执行一次。
        timer.scheduleAtFixedRate(timerTask,1000*5,1000);
    }
}
