package cn.tiakon.learn.java.concurrency.imooc.jimin.synccontainer;

import java.util.Iterator;
import java.util.Vector;

/**
 * Vector 为同步容器，而非线程安全的容器，某些场景下有可能为线程不安全。
 */
public class VectorExample3 {


    // 禁止使用此方法遍历集合时更新集合。可使用标记，遍历结束后再删除。
    // java.util.ConcurrentModificationException
    private static void test1(Vector<Integer> vector) {
        for (Integer integer : vector) {
            if (integer.equals(3)) {
                vector.remove(integer);
            }
        }
    }


    // 禁止使用此方法遍历集合时更新集合。
    // java.util.ConcurrentModificationException
    private static void test2(Vector<Integer> vector) {
        Iterator<Integer> iterator = vector.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next.equals(3)) {
                vector.remove(next);
            }
        }
    }
    // success 推荐
    private static void test3(Vector<Integer> vector) {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i).equals(3)) {
                vector.remove(i);
            }
        }
    }

    public static void main(String[] args)  {

        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test3(vector);

    }

}
