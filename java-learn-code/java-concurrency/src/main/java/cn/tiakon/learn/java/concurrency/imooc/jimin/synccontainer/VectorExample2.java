package cn.tiakon.learn.java.concurrency.imooc.jimin.synccontainer;

import cn.tiakon.learn.java.core.annotation.NonThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Vector;

/**
 * Vector 为同步容器，而非线程安全的容器，某些场景下有可能为线程不安全。
 */
@Slf4j
@NonThreadSafe
public class VectorExample2 {

    public static void main(String[] args) throws InterruptedException {

        Vector<Integer> vector = new Vector<Integer>();

        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            // 当 i = n 时， 线程1先remove第n个元素后，线程2获取第n个元素时会发生异常。
            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });
            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.get(i);
                }
            });

            thread1.start();
            thread2.start();
        }

    }

}
