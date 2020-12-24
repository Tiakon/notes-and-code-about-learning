package cn.tiakon.learn.java.multithread.chapter06;

/**
 * 演示对象发布与逸出
 * 第三种线程安全问题：在构造函数中未初始化完毕就将this赋值。
 *
 * <p>
 * last time   : 2020/10/9 7:35
 *
 * @author tiankai.me@gmail.com on 2020/10/9 7:35.
 */
public class Demo05_MultiThreadObjectRelease2 {

    static Point point;

    public static void main(String[] args) throws InterruptedException {
        new PointMaker().start();
//        Thread.sleep(10);
        Thread.sleep(105);
        if (point != null) {
            System.out.println(point);
        }
    }

}

class Point {

    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        Demo05_MultiThreadObjectRelease2.point = this;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class PointMaker extends Thread {

    @Override
    public void run() {

        new Point(1, 1);

    }
}
