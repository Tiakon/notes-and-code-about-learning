package cn.tiakon.learn.java.multithread.chapter01;

/**
 *
 *
 * 使用 Runable 方式创建线程。
 *
 * @author Administrator
 */
public class Demo01_RunableStyle {
    public static void main(String[] args) {
        RunableStyle runableStyle = new RunableStyle();
        Thread thread = new Thread(runableStyle);
        thread.start();
    }

}

class RunableStyle implements Runnable{
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        System.out.println(Thread.currentThread().getName().concat(" 使用 Runable 方式创建线程..."));
    }
}


