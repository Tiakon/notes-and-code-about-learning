package cn.tiakon.learn.java.concurrency.imooc.jimin.singleton;

import cn.tiakon.learn.java.core.annotation.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在类装载时进行创建
 * 注意:静态域与静态代码的顺序不同，执行顺序也会不同。
 */
@ThreadSafe
public class SingletonExample6 {

    // 私有构造函数
    private SingletonExample6() {
    }

    // 单例对象
    private static SingletonExample6 instance = null;

    static {
        instance = new SingletonExample6();
    }

    // 静态的工厂方法
    public static SingletonExample6 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(SingletonExample6.getInstance().hashCode());
        System.out.println(SingletonExample6.getInstance().hashCode());
    }
}
