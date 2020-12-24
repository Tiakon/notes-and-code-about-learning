package cn.tiakon.learn.java.concurrency.imooc.jimin.atomic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import cn.tiakon.learn.java.core.annotation.ThreadSafe;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdater:可用于更新类的属性，要求属性必须使用 【volatile】 修饰，且不能被 【static】 所修饰。
 *
 * @author Administrator
 */
@Slf4j
@ThreadSafe
public class AtomicExample4 {

    private static AtomicIntegerFieldUpdater<AtomicExample4> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicExample4.class, "count");

    @Getter
    public volatile int count = 100;

    public static void main(String[] args) {
        AtomicExample4 example5 = new AtomicExample4();
        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 1, {}", example5.getCount());
        }
        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 2, {}", example5.getCount());
        } else {
            log.info("update failed, {}", example5.getCount());
        }
    }
}