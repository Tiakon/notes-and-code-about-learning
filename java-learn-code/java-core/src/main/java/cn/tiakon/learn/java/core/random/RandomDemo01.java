package cn.tiakon.learn.java.core.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// https://www.cnblogs.com/54chensongxia/p/12294994.html
public class RandomDemo01 {
    public static void main(String[] args) {
//        method01();
//        System.out.println("===========================");
//        method02();
        method03();
    }

    /**
     * Random类的局限性,只要这个种子给的一样，产生的随机数序列就是一样的。
     * 每个Random实例里面都有一个原子性的种子变量用来记录当前的种子值，当要生成新的随机数时需要根据当前种子计算新的种子并更新回原子变量。在多线程下使用单个Random实例生成随机数时，当多个线程同时计算随机数来计算新的种子时，多个线程会竞争同一个原子变量的更新操作，由于原子变量的更新是CAS操作，同时只有一个线程会成功，所以会造成大量线程进行自旋重试，这会降低并发性能。
     * 分析到这里我们可以看出Random的局限性并不是线程安全的问题，而是在大量线程并发的时候，通过CAS机制更新随机数种子会导致大量线程自旋，耗费CPU性能，导致系统吞吐量下降。
     * last time   : 2021/5/27 11:26
     *
     * @author tiankai.me@gmail.com on 2021/5/27 11:26.
     */
    public static void method01() {
        Random random1 = new Random(100);
        for (int i = 0; i < 10; i++) {
            System.out.println(random1.nextInt(5));
        }
        System.out.println("-------------");
        Random random2 = new Random(100);
        for (int i = 0; i < 10; i++) {
            System.out.println(random2.nextInt(5));
        }
    }

    /**
     * ThreadLocalRandom类是JDK 7在JUC包下新增的随机数生成器，它弥补了Random类在多线程下的缺陷。
     * ThreadLocal通过让每一个线程复制一份变量，使得在每个线程对变量进行操作时实际是操作自己本地内存里面的副本，从而避免了对共享变量进行同步。
     * 实际上ThreadLocalRandom的实现也是这个原理，Random的缺点是多个线程会使用同一个原子性种子变量，从而导致对原子变量更新的竞争。
     *
     * @author tiankai.me@gmail.com on 2021/5/27 11:27.
     */
    public static void method02() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(100));
        }
        System.out.println("---------------------------------");
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(100));
        }
    }

    public static void method03() {
        for (int i = 0; i < 10; i++) {
            System.out.println(ThreadLocalRandom.current().nextInt());
        }
    }

}
