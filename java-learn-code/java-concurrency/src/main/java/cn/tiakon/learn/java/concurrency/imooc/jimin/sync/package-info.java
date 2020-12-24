package cn.tiakon.learn.java.concurrency.imooc.jimin.sync;

/**
 * synchronized 的作用：多线程同时执行时，保证代码块内的内容在执行时只能被一个线程运行。
 * <p>
 * synchronized 的四种修饰方式，两种作用域:
 * 1. 修饰一个代码块:作用于所调用的对象。 {@link cn.tiakon.learn.java.concurrency.imooc.jimin.sync.SynchronizedExample1}
 * 2. 修饰一个方法:作用于所调用的对象。 {@link cn.tiakon.learn.java.concurrency.imooc.jimin.sync.SynchronizedExample1}
 * 3. 修饰一个静态方法:作用于所有对象。 {@link cn.tiakon.learn.java.concurrency.imooc.jimin.sync.SynchronizedExample3}
 * 4. 修饰一个类:作用于所有对象。 {@link cn.tiakon.learn.java.concurrency.imooc.jimin.sync.SynchronizedExample4}
 * <p>
 * synchronized 的使用总结:
 * 1. synchronized 修饰整个方法的代码块时，作用等同于修饰方法的用法。
 * 2. 子类继承父类时，子类调用父类的同步方法时需要显式的声明 synchronized 关系字，同步作用才会生效。
 * 3. synchronized 修饰了一个类的代码块包含了整个方法体时，作用等同于修饰一个静态方法。
 */