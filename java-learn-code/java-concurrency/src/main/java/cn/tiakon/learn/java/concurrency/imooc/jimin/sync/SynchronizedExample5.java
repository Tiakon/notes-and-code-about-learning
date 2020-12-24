package cn.tiakon.learn.java.concurrency.imooc.jimin.sync;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.GuardedBy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class SynchronizedExample5 {

    @GuardedBy("this")
    private int i = 0;

    private void test1(int j) {
        for (; i < 10; i++) {
            log.info(">> test1 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        SynchronizedExample5 example1 = new SynchronizedExample5();
        SynchronizedExample5 example2 = new SynchronizedExample5();

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
