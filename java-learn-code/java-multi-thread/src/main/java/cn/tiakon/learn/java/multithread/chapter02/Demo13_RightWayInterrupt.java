package cn.tiakon.learn.java.multithread.chapter02;

/**
 * last time   : 2020/9/24 16:51
 *
 * @author tiankai.me@gmail.com on 2020/9/24 16:51.
 */
public class Demo13_RightWayInterrupt {

    public static void main(String[] args) {

        Thread threadOne = new Thread(() -> {
            for (; ; ) {
            }
        });

        try {
            // 启动线程
            threadOne.start();  // false

            // 设置中断标志
            threadOne.interrupt(); //   true

            // 获取中断标记
            System.out.println("获取中断标记: " + threadOne.isInterrupted()); // true

            // interrupted() 的作用对象是调用它的线程，与实例对象无关 源码 =》（currentThread().isInterrupted）
            System.out.println("获取中断标记并重置: " + threadOne.interrupted()); // false

            System.out.println("获取中断标记并重置: " + Thread.interrupted()); // false

            // threadOne.interrupted() 与 Thread.interrupted() 等效
            System.out.println("获取中断标记: " + threadOne.isInterrupted()); // true

            threadOne.join();

            System.out.println("Main thread is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
