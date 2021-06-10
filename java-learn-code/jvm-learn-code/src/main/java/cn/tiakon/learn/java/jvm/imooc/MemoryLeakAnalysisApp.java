package cn.tiakon.learn.java.jvm.imooc;

import java.util.ArrayList;
import java.util.List;

/**
 * 结合 visualVM 和 jmc 来分析内存泄露、线程查看、热点方法查看，垃圾回收查看。
 * -Xms50m -Xmx200M
 * <p>
 * last time   : 2021/1/22 22:16
 *
 * @author tiankai.me@gmail.com on 2021/1/22 22:16.
 */
public class MemoryLeakAnalysisApp {
    public static void main(String[] args) {

        try {
            Thread.sleep(1000 * 10);
            List list = new ArrayList<>();
            for (int i = 0; i < 5000*1024; i++) {
                list.add(new A());
                if (i % 20 == 0) {
                    Thread.sleep(100);
                }
            }

            System.out.println("end...");

            Thread.sleep(Integer.MAX_VALUE);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class A {
    private byte[] bs = new byte[1024];
}