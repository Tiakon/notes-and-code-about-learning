package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 可重入性度测试2:调用类内其它的方法。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo011_SynchronizedRecursion {

    public static void main(String[] args) throws InterruptedException {

        Demo011_SynchronizedRecursion runnable1 = new Demo011_SynchronizedRecursion();

        runnable1.method1();

        System.out.println(">> finished...");
    }

    private synchronized void method1() {
        System.out.println(">> method1");
        method2();
    }

    private synchronized void method2() {
        System.out.println(">> method2");
    }

}

