package cn.tiakon.learn.java.multithread.chapter01;

/**
 * 同时使用 Thread 与 Runable 的方式创建线程。
 * Thread子类的run()会覆盖 Runable实现类的run方法。
 *
 * @author Administrator
 */
public class Demo03_ThreadAndRunable {

    public static void main(String[] args) {
        RunableStyle runableStyle = new RunableStyle();
        ThreadStyle threadStyle = new ThreadStyle(runableStyle);
        threadStyle.start();
    }

}


