package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 最佳实践2：在 cache 之后 再调用 Thread.currentThread().interrupt(); 来恢复设置中断状态，以便于再后续的执行中，依然能够检查到刚才发生了中断。
 * 回到 Demo07_RightWayStopThreadInProduct 补上中断，让它跳出。
 * last time   : 2020/9/24 14:05
 *
 * @author tiankai.me@gmail.com on 2020/9/24 14:05.
 */
public class Demo08_RightWayStopThreadInProduct2 implements Runnable {

    @Override
    public void run() {
        // 检查响应中断标记
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(">> 发生中断任务结束...");
                break;
            }

            System.out.println(">> go");
            throwInMethod();
        }
    }

    public void throwInMethod() {
        try {
            // sleep 响应中断后，会清除中断标记。
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Demo08_RightWayStopThreadInProduct2());
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();

    }

}
