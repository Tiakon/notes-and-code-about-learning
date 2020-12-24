package cn.tiakon.learn.java.multithread.chapter03;

/**
 * Sleep不释放锁
 * last time   : 2020/10/6 17:34
 *
 * @author tiankai.me@gmail.com on 2020/10/6 17:34.
 */
public class Demo08_SleepSync implements Runnable {
    public static void main(String[] args) {
        Demo08_SleepSync sleepSync = new Demo08_SleepSync();
        new Thread(sleepSync).start();
        new Thread(sleepSync).start();
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName().concat(" : 获得 monitor 锁..."));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName().concat(" : sleep 结束..."));
        }
    }
}
