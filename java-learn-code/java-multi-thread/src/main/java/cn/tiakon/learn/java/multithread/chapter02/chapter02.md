## 2. 启动线程的正确和错误的方式

### 2.1 start()和run()的比较。
代码...

### 2.2 start()方法原理解读。

 start() 源码解析,start()的执行流程：

  1. 启动新线程检查线程状态。
  2. 加入线程组。
  3. 调用本地方法(native) start0()。

 start() 方法含义：
 1. 启动新线程（调用start()后，让主函数请求告诉jvm，在jvm空闲时启动新线程）。
 a. 调用 start() 后线程并不一定立刻运行，线程何时运行，是由线程调度器所决定，当饥饿时就可能不会立刻执行。
 b. 调用 start() 的顺序并不代表执行顺序。
 2. 准备工作。
 3. 不能重复启动。
### 2.3 run()方法原理解读

run()就是一个普通的访问，直接调用不会创建线程。

### 2.4 常见面试问题
1. 一个线程两次调用start()方法会出现什么情况？为什么？
   - 会抛出非法线程状态异常(java.lang.IllegalThreadStateException);
   - 线程执行start方法后会检查线程状态，第一次启动线程状态已修改成非0状态，第二次调用start()不等于0就会抛出异常。
   - 介绍线程状态。

2. 既然 start() 方法回调用run() 方法，为什么我们选择调用start() 方法，而不是直接调用 run() 方法？
    - 因为调用 start() 才会启动一个线程。而调用 run() 则是在主线程中调用了一个方法，并没有真正的启动一个线程。

## 3.  如何正确停止线程？

### 3.1 原理介绍：

   使用interrupt来通知，而不是强制。

### 3.2 最佳实践：如何正确停止线程?

#### 1 通常线程会在什么情况下停止。

1. 所有代码执行完毕。
2. 异常出现。

#### 2 正确方法：用 interruput 来请求停止线程 。

1.  通常线程会在什么情况下停止。
2.  线程可能被阻塞。
3.  如果线程再每次迭代后都阻塞。
4.  whlie内try/catch的问题。

#### 3 实际生产开发时要注意的编码习惯

sleep 响应中断后，会清除中断标记。

1. 优先选择：传递中断

2. 不想或无法传递：恢复中断。

3. 不应屏蔽中断。  

4. 可以为了响应中断而抛出InterruptedException的常见方法列表总结：

   ```java
   Object.wait()/wait(long)/wait(long,int)
   Thread.sleep()/wait(long)/wait(long,int)
   Thread.join()/wait(long)/wait(long,int)
   java.util.concurrent.BlockingQueue.take()/put(E)
   java.util.concurrent.locks.Lock.lockInterruptibly()
   java.util.concurrent.CountDownLatch.await()
   java.util.concurrent.CyclicBarrier.await()
   java.util.concurrent.Exchanger.exchange(V)
   java.nio.channels.InterruptibleChannel 相关方法
   java.nio.channels.Selector 相关方法
   ```

   

###    3.3 正确停止带来的好处?

#### 1. 错误的停止方法

1. 被弃用的stop、suspend和resume方法。
   - ,stop已被弃用。使用 stop() 停止线程，会导致线程运行中突然停止。
2. 用volatile设置boolean标记位。
   - run() 循环逻辑中陷入阻塞时，volatile是无法停止线程的。

#### 2. 停止线程相关的重要函数解析

- 中断线程
- 判断是否已被中断
  - static boolean interrupted()
  - boolean isInterrupted()
  - 举例说明，注意Thread.interrupted的目标对象是当前线程，而不管本方法是来自于哪个对象。

###  3.4 彩蛋 

Java异常体图

### 3.5常见面试问题。 

1. 如何停止一个线程？
   - 原理：用interrupt来请求、好处。
   - 想停止线程，要求请求方、被停止方、子方法被调用方相互配合。
   - 最后再说错误的方法：stop/suspend已废弃，volatile的boolean类型标记无法处理长时间阻塞的情况。 
2. 如何处理不可中断的阻塞（例如抢锁时Reentrant.lock()或者SocketI/O是无法响应中断，那应该怎么让该线程停止呢？）
   - 并没有一个通用的解决方案。针对特定的情况，我们使用特定的方法，尽可能的做到响应中断。

### 3.6彩蛋：

 JRE和JDK是什么关系？

 Java的SDK和JDK一样吗？

 Java 7 是 Java SE7吗，SE是什么意思？

 Java 8 和 JDK1.8是什么关系，是同一个东西吗？ 

 