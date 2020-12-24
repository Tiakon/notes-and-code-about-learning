package cn.tiakon.learn.java.multithread.chapter04;

/**
 * 线程 ID 从1开始，JVM运行起来后(Jvm 会创建多个线程)，我们自己创建的线程的id早已不是2。
 * last time   : 2020/10/8 16:48
 *
 * @author tiankai.me@gmail.com on 2020/10/8 16:48.
 */
public class Demo01_ThreadId {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println(Thread.currentThread().getName() + " : " + Thread.currentThread().getId());
        System.out.println(thread.getName() + " : " + thread.getId());
    }
}
