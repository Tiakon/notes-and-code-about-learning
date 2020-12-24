package cn.tiakon.learn.java.multithread.chapter06;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示对象发布与逸出
 * 第四种线程安全问题：在构造函数中初始化对象。（获得数据库连接池）
 * 会导致时间不同，结果不同。
 *
 * <p>
 * last time   : 2020/10/9 7:35
 *
 * @author tiankai.me@gmail.com on 2020/10/9 7:35.
 */
public class Demo07_MultiThreadObjectRelease4 {

    private Map<String, String> weeks;

    public Demo07_MultiThreadObjectRelease4() {
        new Thread(() -> {
            weeks = new HashMap<String, String>();
            weeks.put("1", "周一");
            weeks.put("2", "周二");
            weeks.put("3", "周三");
            weeks.put("4", "周四");
            weeks.put("5", "周五");
        }).start();
    }

    public Map<String, String> getWeeks() {
        return weeks;
    }

    public static void main(String[] args) {
        Demo07_MultiThreadObjectRelease4 d4 = new Demo07_MultiThreadObjectRelease4();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<String, String> weeks = d4.getWeeks();
        System.out.println(weeks.get("1"));
    }

}
