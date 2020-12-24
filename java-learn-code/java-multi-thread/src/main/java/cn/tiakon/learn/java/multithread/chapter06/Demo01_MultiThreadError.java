package cn.tiakon.learn.java.multithread.chapter06;

/**
 * 数据争用案例：
 * 演示多线程a++结果错误
 * <p>
 * last time   : 2020/10/9 7:35
 *
 * @author tiankai.me@gmail.com on 2020/10/9 7:35.
 */
public class Demo01_MultiThreadError implements Runnable {


    static int count = 0;
    static int countLast = 0;

    @Override
    public void run() {
//        while (index < 10000) {
//            index++;
//        }
        for (int i = 0; i < 10000; i++) {
            count++;
            countLast = count;
        }

    }

    public static void main(String[] args) {
        Demo01_MultiThreadError mutiThreadError = new Demo01_MultiThreadError();
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
        System.out.println(count);
    }

}
