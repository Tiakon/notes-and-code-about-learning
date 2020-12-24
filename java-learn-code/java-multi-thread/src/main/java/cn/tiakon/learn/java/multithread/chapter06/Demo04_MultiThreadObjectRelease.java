package cn.tiakon.learn.java.multithread.chapter06;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示对象发布与逸出
 * 第三种线程安全问题：方法返回 private 对象。
 *
 * <p>
 * last time   : 2020/10/9 7:35
 *
 * @author tiankai.me@gmail.com on 2020/10/9 7:35.
 */
public class Demo04_MultiThreadObjectRelease {

    private Map<String, String> weeks;

    public Demo04_MultiThreadObjectRelease() {
        weeks = new HashMap<String, String>();
        weeks.put("1", "周一");
        weeks.put("2", "周二");
        weeks.put("3", "周三");
        weeks.put("4", "周四");
        weeks.put("5", "周五");
    }

    public Map<String, String> getWeeks() {
        return weeks;
    }

    /**
     * 返回副本，修改当前对象不会影响到原来的对象。
     * last time   : 2020/10/13 12:04
     *
     * @author tiankai.me@gmail.com on 2020/10/13 12:04.
     */
    public Map<String, String> getWeeksImproved() {
        return new HashMap<>(weeks);
    }

    public static void main(String[] args) {
        Demo04_MultiThreadObjectRelease d4 = new Demo04_MultiThreadObjectRelease();
//        Map<String, String> weeks = d4.getWeeks();
        System.out.println(d4.getWeeksImproved().get("1"));
        d4.getWeeksImproved().remove("1");
        System.out.println(d4.getWeeksImproved().get("1"));
    }

}
