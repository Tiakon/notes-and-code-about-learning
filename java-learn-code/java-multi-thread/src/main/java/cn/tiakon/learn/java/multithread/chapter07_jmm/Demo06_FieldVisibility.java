package cn.tiakon.learn.java.multithread.chapter07_jmm;

/**
 * 演示：synchronzied 的近朱者赤效果。
 * <p>
 * last time   : 2020/10/13 18:35
 *
 * @author tiankai.me@gmail.com on 2020/10/13 18:35.
 */
public class Demo06_FieldVisibility {
    //    volatile
    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;

    private void change() {
        a = 5;
        b = 6;
        c = 7;
        synchronized (this) {
            d = 8;
        }
    }

    private void print() {
        int aa;
        synchronized (this) {
            aa = a;
        }
        int bb = b;
        int cc = c;
        int dd = d;
        // 这里 b 作为刷新之前的变量的触发器
        System.out.println("a: " + aa + ", b: " + bb + ", c: " + cc + ", d: " + dd);
    }

    public static void main(String[] args) {

        while (true) {
            Demo06_FieldVisibility fieldVisibility = new Demo06_FieldVisibility();
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
