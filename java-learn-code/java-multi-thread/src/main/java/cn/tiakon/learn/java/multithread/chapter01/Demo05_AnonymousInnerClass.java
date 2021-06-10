package cn.tiakon.learn.java.multithread.chapter01;

/**
 *
 * 通过匿名内部类的方式创建线程池。
 *
 * @author Administrator
 */
public class Demo05_AnonymousInnerClass {

    public static void main(String[] args) {

        new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

    }

}
