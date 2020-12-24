package cn.tiakon.learn.java.concurrency.imooc.jimin.singleton;


/**
 * 安全发布对象:
 * 1.在静态初始化函数或静态代码块中初始化一个对象的引用。
 * {@link cn.tiakon.learn.java.concurrency.imooc.jimin.singleton.SingletonExample2}
 * {@link cn.tiakon.learn.java.concurrency.imooc.jimin.singleton.SingletonExample6}
 * 2.将对象的引用保存在volatile类型或AtomicReference对象中。
 * {@link cn.tiakon.learn.java.concurrency.imooc.jimin.singleton.SingletonExample5}
 * 3.将对象的引用保存在某个正确构造对象的final类型域中。
 * 4.将对象的引用保存到一个由锁保护的域中。
 * {@link cn.tiakon.learn.java.concurrency.imooc.jimin.singleton.SingletonExample3}
 * 5.使用枚举模式也可以实现对象的安全发布。
 * {@link cn.tiakon.learn.java.concurrency.imooc.jimin.singleton.SingletonExample7}
 */