package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 可重入性度测试1:递归调用本方法。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo010_SynchronizedRecursion {

    int a = 0;

    public static void main(String[] args) throws InterruptedException {

        Demo010_SynchronizedRecursion runnable1 = new Demo010_SynchronizedRecursion();

        runnable1.method1();

        System.out.println(">> finished...");
    }

    private synchronized void method1() {
        System.out.println(">> method1 a:" + a);
        if (a == 0) {
            a++;
            method1();
        }
    }

}

