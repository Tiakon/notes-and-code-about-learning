package cn.tiakon.learn.java.multithread.chapter02;

/**
 * 最佳实践：cache 了 InterruptedException 之后优先选择： 在方法签名中抛出异常，那么在 run() 就会强制 try/cache 。
 * last time   : 2020/9/24 14:05
 *
 * @author tiankai.me@gmail.com on 2020/9/24 14:05.
 */
public class Demo07_RightWayStopThreadInProduct implements Runnable {

    @Override
    public void run() {
        // 检查响应中断标记
        while (true && !Thread.currentThread().isInterrupted()) {
            System.out.println(">> go");
//            throwInMethod();
            // run () 无法抛出 checked Exception，只能用try/cache。
            try {
                throwInMethodPlus();
            } catch (InterruptedException e) {
                System.out.println(">> 保存日志等操作...");
                e.printStackTrace();
            }
        }
    }

    // 此写法错误，不应该try/catch异常。
    public void throwInMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 应该抛出异常。
    public void throwInMethodPlus() throws InterruptedException {
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Demo07_RightWayStopThreadInProduct());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

    }

}
