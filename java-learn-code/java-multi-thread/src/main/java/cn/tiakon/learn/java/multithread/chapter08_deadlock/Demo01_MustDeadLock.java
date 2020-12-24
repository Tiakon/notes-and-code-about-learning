package cn.tiakon.learn.java.multithread.chapter08_deadlock;


/**
 * 一定发生死锁
 * last time   : 2020/11/3 8:10
 *
 * @author tiankai.me@gmail.com on 2020/11/3 8:10.
 */
public class Demo01_MustDeadLock {

    public static void main(String[] args) {

        Object o1 = new Object();
        Object o2 = new Object();

        MustDeadLock mustDeadLock01 = new MustDeadLock(o1,o2,1);
        MustDeadLock mustDeadLock02 = new MustDeadLock(o1,o2,2);

        Thread thread1 = new Thread(mustDeadLock01);
        Thread thread2 = new Thread(mustDeadLock02);

        thread1.start();
        thread2.start();

    }
}

class MustDeadLock implements Runnable {

    private Object o1;
    private Object o2;
    private int flag;

    public MustDeadLock(Object o1, Object o2, int flag) {
        this.o1 = o1;
        this.o2 = o2;
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag == 1) {
            System.out.println("flag:" + flag);

            synchronized (o1) {
                System.out.println("进入o1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println("获取o2");
                }
            }

        }
        if (flag == 2) {
            System.out.println("flag:" + flag);
            synchronized (o2) {
                System.out.println("进入o2");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("获取o1");
                }
            }
        }
    }
}