package cn.tiakon.learn.java.multithread.chapter06;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据争用案例：
 * 演示多线程a++结果错误
 * 演示计数不准确，找出聚体出错位置
 * <p>
 * last time   : 2020/10/9 7:35
 *
 * @author tiankai.me@gmail.com on 2020/10/9 7:35.
 */
public class Demo02_MultiThreadErrorPlus implements Runnable {

    static int index = 0;
    final boolean[] marked = new boolean[100000];
    static AtomicInteger realCount = new AtomicInteger();
    static AtomicInteger wrongCount = new AtomicInteger();
    static volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    static volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    @Override
    public void run() {
        this.countIndex();
    }

    /**
     * 多线程a++结果错误原因分析：
     * 1. 数据争用。线程1中的变量副本被修改，没有及时同步到主内存中，导致线程2中的变量副本还是未修改的值。
     * 2. 竞态条件
     * 3. 数据同步
     * <p>
     * last time   : 2020/10/9 21:47
     *
     * @author tiankai.me@gmail.com on 2020/10/9 21:47.
     */
    public void countIndex() {
        marked[0]=true;
        for (int i = 0; i < 10000; i++) {
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            index++;
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            realCount.incrementAndGet();
            synchronized (this) {
                // 解决：
                // 1. 两个线程先后为同一个值时。if判断解决
                // 2. 两个线程同时等于某一个值时。synchronized解决
                if (marked[index] && marked[index - 1]) {
                    System.out.println("发生错误: " + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }
        }
    }

    public static void main(String[] args) {
        Demo02_MultiThreadErrorPlus mutiThreadError = new Demo02_MultiThreadErrorPlus();

        Thread t1 = new Thread(mutiThreadError);
        Thread t2 = new Thread(mutiThreadError);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("表面统计次数: " + index);
        System.out.println("真正统计次数: " + realCount.get());
        System.out.println("统计错误次数: " + wrongCount.get());
    }

}
