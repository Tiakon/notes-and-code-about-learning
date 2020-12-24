package cn.tiakon.learn.java.multithread.chapter01;

/**
 * 使用lamdba表达式创建线程。
 *
 * @author Administrator
 */
public class Demo06_Lambda {
    public static void main(String[] args) {
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
    }
}
