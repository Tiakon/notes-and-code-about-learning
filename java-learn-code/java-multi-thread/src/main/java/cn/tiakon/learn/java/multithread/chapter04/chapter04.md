# 6. 线程的各个属性 

##### 考考你

- 什么时候我们需要设置守护线程？
- 我们应该如何应用线程优先级来帮助程序运行？有哪些禁忌？
- 不同的操作系统如何处理优先级问题？

### 6.1 线各属性纵览 

1. 线程ID:每个线程的自己的ID，用于表示不同的线程。（不能修改）
2. 线程名字：作为让用户或程序员在开发、调试或运行过程中，更容易区分每个不同的线程。定位问题等。
3. 守护线程：true代表是守护线程，false代表非守护线程，也就是用户线程。
4. 线程优先级:优先级这个属性会告诉线程调度器，用户希望那些线程相对多运行、哪些少运行。
5. 各属性总结

##### 1. 线程ID

​	 线程 ID 从1开始，JVM运行起来后(Jvm 会创建多个线程)，我们自己创建的线程的id早已不是2。

##### 2. 线程名字

- 默认线程名字源码分析
- 修改线程名字

##### 3. 守护线程

- 作用：给用户提供服务。
- 3个特性：
  - 线程类型默认集成自父线程
  - 由JVM启动。
  - 不影响Jvm退出。
- 守护线程和普通线程的区别
  - 整体无区别
  - 唯一区别在于是否能影响到Jvm的离开（用户线程会影响，而守护线程不需要jvm等待其执行结束，可以先退出）。
- 常见面试问题
  - 守护线程与普通线程的区别
    - 整体没有太大区别，区别在于是否会影响到jvm的退出，以及作用不同。
    - 普通线程：执行用户逻辑的。
    - 守护线程：服务于用户的。
  - 我们是否需要给线程设置为守护线程？
    - 我们不应该将线程设置为守护线程，如果将用户线程设置成守护线程，会因为jvm提前退出，导致用户执行io被强行中止，会出现数据不一致的问题。

##### 4. 线程优先级

- 10个级别，默认是5（开发中没必要进行修改，默认5就行）。

- 1、5、10 越大优先级越高。

- 程序设计不应依赖于优先级
  - 不同操作系统优先级别不一样；优先级会随着操作系统的变化，而变化导致程序变得不可靠。
  - 优先级会被操作系统更改。

### 6.2 各属性总结

1. 线程ID:每个线程的自己的ID，用于表示不同的线程。（不能修改）
2. 线程名字：作为让用户或程序员在开发、调试或运行过程中，更容易区分每个不同的线程。定位问题等。
3. 守护线程：true代表是守护线程，false代表非守护线程，也就是用户线程。
4. 线程优先级:优先级这个属性会告诉线程调度器，用户希望那些线程相对多运行、哪些少运行。



| 属性名称 | 用途               | 注意事项                                              |
| -------- | ------------------ | ----------------------------------------------------- |
| 编号     | 标识不同的线程     | 被后续创建的线程的使用；唯一性；不允许被修改          |
| 名称     | 定位问题           | 清晰有意义的名字里默认的名称                          |
| 守护线程 | 守护线程、用户线程 | 二选一；继承父线程；setDaemon                         |
| 优先级   | 相对多运行         | 默认和父线程优先级相等，共有10个等级默认是5；不应依赖 |



### 6.3 面试常见问题

- 什么时候我们需要设置守护线程？
  - 通常情况下我们不需要设置，jvm提供的守护线程足够我们使用。
- 我们应该如何应用线程优先级来帮助程序运行？有哪些禁忌？
  - 我们不应该用优先级来设计程序，因为不同的操作系统优先等级不一样。
- 不同的操作系统如何处理优先级问题？
  - 不同的操作系统对优先级的映射和调度都不一样，很容易被操作系统忽略，所以我们不应该用优先级做我们的业务逻辑。