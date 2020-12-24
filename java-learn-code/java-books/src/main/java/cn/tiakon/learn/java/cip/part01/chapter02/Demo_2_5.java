package cn.tiakon.learn.java.cip.part01.chapter02;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 0 @guardedby同步注解:
 * 1、@GuardedBy( "this" ) 受对象内部锁保护
 * 2、@GuardedBy( "fieldName" ) 受 与fieldName引用相关联的锁 保护。
 * 3、@GuardedBy( "ClassName.fieldName" ) 受 一个类的静态field的锁 保存。
 * 4、@GuardedBy( "methodName()" ) 锁对象是 methodName() 方法的返值，受这个锁保护。
 * 5、@GuardedBy( "ClassName.class" ) 受 ClassName类的直接锁对象保护。而不是这个类的某个实例的锁对象。
 *
 * @author Administrator
 */
@Slf4j
public class Demo_2_5 {

    @GuardedBy("this")
    private int i = 0;

    private void test1(int j) {
        for (; i < 10; i++) {
            log.info(">> test1 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Demo_2_5 example1 = new Demo_2_5();
        Demo_2_5 example2 = new Demo_2_5();

        // 模拟多个线程，同时执行一段代码时。
        executorService.execute(() -> {
            example1.test1(1);
//            SynchronizedExample2.test2(1);
        });
        executorService.execute(() -> {
            example1.test1(2);
//            SynchronizedExample2.test2(2);
        });

        executorService.shutdown();
    }

}

class UnsafeCachingFactorizer {

    private final AtomicReference<Integer> lastNumber = new AtomicReference<Integer>();
    private final AtomicReference<Integer[]> lastFactors = new AtomicReference<Integer[]>();

    public void service(Integer i) {
        Integer[] factors = factors(i);
        System.out.println(Arrays.toString(factors));
    }

    private Integer[] factors(Integer input) {
        Integer[] arr = new Integer[2];
        for (Integer x = 2; x < input; x++) {
            if (input % x == 0) {

                int y = input / x;
                arr[0] = x;
                arr[1] = y;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        UnsafeCachingFactorizer factorizer = new UnsafeCachingFactorizer();
        factorizer.service(100);
    }

}