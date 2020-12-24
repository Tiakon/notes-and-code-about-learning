package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 不使用同步锁会出现的问题。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo01_DisappearRquests implements Runnable {

    static int i = 0;

    public static void main(String[] args) throws InterruptedException {

        Demo01_DisappearRquests runnable = new Demo01_DisappearRquests();

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }
}

