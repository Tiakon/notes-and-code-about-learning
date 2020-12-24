package cn.tiakon.learn.java.multithread.chapter03;

/**
 * 演示当前线程引用的用法：Thread.currentThread()
 * last time   : 2020/10/8 14:36
 *
 * @author tiankai.me@gmail.com on 2020/10/8 14:36.
 */
public class Demo13_CurrentThread implements Runnable {

    public static void main(String[] args) {

        new Demo13_CurrentThread().run();
        new Thread(new Demo13_CurrentThread()).start();
        new Thread(new Demo13_CurrentThread()).start();

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
