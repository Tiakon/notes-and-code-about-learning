package cn.tiakon.learn.java.cip.part01.chapter01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo_1_1 {
}

class UnsafeSequence {

    private int value;

    public int getNext() {
        return value++;
    }

    public static void main(String[] args) {

        UnsafeSequence sequence = new UnsafeSequence();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 5000; i++) {
            // 多线程之间共享变量，引发了线程安全问题。
            executorService.execute(() -> sequence.getNext());
        }

        executorService.shutdown();

        System.out.println(sequence.value);

    }

}