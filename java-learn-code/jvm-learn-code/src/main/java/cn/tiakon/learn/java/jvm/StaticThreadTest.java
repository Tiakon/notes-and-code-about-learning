package cn.tiakon.learn.java.jvm;

/**
 * jvm 保证多线程环境下，类加载只进行一次。
 *
 * @author Administrator
 */
public class StaticThreadTest {




    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " 开始运行....");
            StaticBlockThreadTest staticBlockThreadTest = new StaticBlockThreadTest();
        };

        Thread thread1 = new Thread(runnable);
        thread1.setName("线程一");

        Thread thread2 = new Thread(runnable);
        thread2.setName("线程二");

        thread1.start();
        thread2.start();
    }
}

class StaticBlockThreadTest {
    static {
        if (true) {
            System.out.println(Thread.currentThread().getName() + " 正在处理...");
            while (true) {

            }
        }
    }
}