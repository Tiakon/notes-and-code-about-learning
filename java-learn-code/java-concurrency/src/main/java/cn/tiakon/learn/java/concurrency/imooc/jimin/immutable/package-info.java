package cn.tiakon.learn.java.concurrency.imooc.jimin.immutable;


/**
 * 不变对象需要满足的条件:
 * 1. 对象创建以后其状态就不能修改（通过final修饰创建的对象）。
 * 2. 对象所有域都是final。
 * 3. 对象是正确创建的（在对象创建期间，this引用没有逸出）
 * <p>
 * final 的作用：
 * 1. 修饰类，不可被继承（可参考java.lang.String）
 * 2. 修饰方法，不可被继承.
 * 3. 修饰基本数据类型，值不可以被修改。
 * 4. 修饰引用数据类型，对象的引用不可以被修改。
 */