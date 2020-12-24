package cn.tiakon.learn.java.concurrency.imooc.jimin.threadpool;

import java.util.concurrent.locks.LockSupport;

public class Thread_Demo_01 {

    static Thread t1, t2 = null;

    public static void main(String[] args) {

        char[] aC = "abcdefg".toCharArray();
        char[] aI = "1234567".toCharArray();

        t1 = new Thread(() -> {
            for (char c : aC) {
                System.out.println(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : aI) {
                System.out.println(c);
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        }, "t2");
        t1.start();
        t2.start();

    }
}
