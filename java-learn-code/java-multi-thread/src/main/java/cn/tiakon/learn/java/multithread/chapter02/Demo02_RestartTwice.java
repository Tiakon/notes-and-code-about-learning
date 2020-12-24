package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 重启两次 start() ，第二次调用start() 会抛出 IllegalThreadStateException 异常。
 *
 * @author Administrator
 */
public class Demo02_RestartTwice {
    public static void main(String[] args) {

        Thread thread = new Thread();

        thread.start();
        thread.start();

    }
}
