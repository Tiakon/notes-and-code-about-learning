package cn.tiakon.learn.java.multithread.chapter05;

/**
 * 单线程，抛出、处理、有异常堆栈
 * 多线程，子线程发生异常，会有什么不同？
 * 多线程，子线程发生异常，主线程不会受到影响
 * last time   : 2020/10/8 22:04
 *
 * @author tiankai.me@gmail.com on 2020/10/8 22:04.
 */
public class Demo01_ExceptionInChildThread implements Runnable {

    public static void main(String[] args) {

        new Thread(new Demo01_ExceptionInChildThread()).start();

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }

    }

    @Override
    public void run() {
        throw new RuntimeException("");
    }

}
