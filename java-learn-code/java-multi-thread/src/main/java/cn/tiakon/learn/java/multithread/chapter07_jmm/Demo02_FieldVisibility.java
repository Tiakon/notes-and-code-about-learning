package cn.tiakon.learn.java.multithread.chapter07_jmm;

/**
 * 演示：可见性带来的问题
 * <p>
 * last time   : 2020/10/13 18:35
 *
 * @author tiankai.me@gmail.com on 2020/10/13 18:35.
 */
public class Demo02_FieldVisibility {
//    volatile
    int a = 1;
    int b = 2;

    private void change() {
        a = 3;
        b = a;
    }

    private void print() {
        // 这里 b 作为刷新之前的变量的触发器
        System.out.println("b: " + b + " , a: " + a);
    }

    public static void main(String[] args) {

        while (true) {
            Demo02_FieldVisibility fieldVisibility = new Demo02_FieldVisibility();
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fieldVisibility.change();
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fieldVisibility.print();
            }).start();

            // 1. a=3 , b=3 1线程先执行
            // 2. a=1 , b=2 2线程先执行
            // 3. a=3 , b=2 线程交替执行
            // 4. a=1 , b=3 发生了可见性问题
        }

    }

}
