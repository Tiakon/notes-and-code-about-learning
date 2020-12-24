package cn.tiakon.learn.java.concurrency.imooc.jimin.singleton;

import cn.tiakon.learn.java.core.annotation.NonThreadSafe;

/**
 * 懒汉模式
 */
@NonThreadSafe
public class SingletonExample1 {

    // 私有构造函数
    private SingletonExample1() {
    }

    // 单例对象
    private static SingletonExample1 instance = null;

    public static SingletonExample1 getInstance() {
        if (instance == null) {
            instance = new SingletonExample1();
        }
        return instance;
    }

}
