## 4.  线程的生命周期

##### 1. 有哪6种状态？

New、Runnable、Blocked、Waiting、Timed Waiting、Terminated

##### 2. 每种状态是什么含义？

New：已创建还尚未启动的新线程。

Runnable：可运行

Blocked： 被阻塞

Waiting：等待

Timed Waiting：限期等待

Terminated：终止

##### 3. 状态间的转化图示：

![01-Java线程状态间的转化图示](R:\code\project-source\notes-and-code-about-learning\java-learn-code\java-multi-thread\src\main\java\cn\tiakon\learn\java\multithread\chapter03\01-Java线程状态间的转化图示.png)

##### 4. 阻塞状态是什么？

一般习惯而言，把Blocked（被阻塞）、Waiting（等待）、Timed_waiting(计时等待)都称为阻塞状态不仅仅是 Blocked。

##### 5. 常见面试问题？

线程有哪几种状态？线程的生命周期是什么？
    看图：状态间的转化图示

##### 6.彩蛋：

- JRE和JDK是什么关系？
- Java的SDK和JDK一样吗？
- Java 7 是 Java SE7吗，SE是什么意思？
- Java 8 和 JDK1.8是什么关系，是同一个东西吗？

##### 面试题：

1.为什么线程通信的方法wait(),notifi()和notifyAll()被定义再object类里？而sleep定义在Thread类里？

**2.用3中方式实现生产者模式？**

3.Java SE 8 和Java 1.8和JDK8是什么关系，是同一个东西吗？

4.join和sleep和wait期间线程的状态分别是什么？为什么？




​    

## 5. Thread 和 Object 类中的重要方法详解 ？

#### 1. 方法概览

![02-Thread和Object重要方法概览](R:\code\project-source\notes-and-code-about-learning\java-learn-code\java-multi-thread\src\main\java\cn\tiakon\learn\java\multithread\chapter03\02-Thread和Object重要方法概览.png)



#### 2. wait、notify、notifyAll 方法详解

- 作用、用法（改变线程的状态）：阻塞阶段（**wait**）、唤醒阶段（**notify、notifyAll**）、遇到中断
  - 阻塞阶段：该对象调用 **wait()** ,线程会进入**阻塞阶段**,直到以下4种情况之一发生时，才会被唤醒:

    ​	     a. 另一个线程调用该对象的notify()方法且刚好被唤醒的是本线程；

    ​		 b. 另一个线程调用该对象的notifyAll()方法；

    ​		 c. 过了wait(long timeout)规定的超时时间，如果传入0就是永久等待；

    ​		 e. 线程自身调用了interrupt().

  - 唤醒阶段
            a. notify 会唤醒单个等待的线程。有多个线程在等待，会随机唤醒一个线程。
            b. notifyAll 唤醒所有等待的线程，哪一个会获得锁依赖于操作系统的调度。

  - 遇到中断

- wait、notify、notifyAll 代码演示：4种情况

  - 普通用法
  - notify和notifyAll展示
  - 只释放当前monitor展示

- wait、notify、notifyAll 特点、性质：

  ​	a. 用wait、notify、notifyAll 方法，必须先拥有monitor锁。

  ​	b. 只能唤醒其中一个。

  ​	c. 属于Object类，被 native final 修饰，不可重写。

  ​	d. 类似功能的 Condition。

  ​	e. 同时持有多个锁的情况。

- Wait原理

![03-Wait原理](R:\code\project-source\notes-and-code-about-learning\java-learn-code\java-multi-thread\src\main\java\cn\tiakon\learn\java\multithread\chapter03\03-Wait原理.png)



- 进程状态转化的特殊情况，两种进入Blocked状态的情况：
              a. 等待synchronized修饰的代码块或方法释放时线程会进入Blocked。
              b. 调用Object.wait后重新被唤醒，进入synchronized修饰的代码块或方法等待其释放时，又会进入Blocked。
- 注意：如果发生异常，可以直接跳到终止Terminated状态，不必再遵循路径。比如可以从Waiting直接到Terminated。

![04-状态转化的特殊情况](R:\code\project-source\notes-and-code-about-learning\java-learn-code\java-multi-thread\src\main\java\cn\tiakon\learn\java\multithread\chapter03\04-状态转化的特殊情况.png)

#### 3. 彩蛋与面试题

##### 1. 彩蛋

Java SE : Java 标准版
Java EE : Java 企业版
Java ME : Java 移动版

JVM ： Java Virtual Machine     , Java 虚拟机
JRE ： Java Runtime Environment , Java 运行环境(包括JVM，还有Java类库等...)
JDK ： Java Development Kit     , Java 开发环境(包括JRE，还有一些诊断工具等...)

Java版本升级都包括了哪些东西的升级？ 包括类的升级、Jvm的升级。
        
Java SE 8 和Java 1.8和JDK8是什么关系，是同一个东西吗？

#### 4. sleep方法详解

**作用：**

​    在线程在预期的时间执行，其它时间不要占用cpu资源。不释放锁，包括synchronized和lock，和wait不同。

**sleep 方法响应中断：**

       1. 抛出InterruptException。
       2. 清除中断状态。
       3. 第二种写法（更优雅） Demo10_SleepInterrupted

**一句话总结（讲讲sleep方法的特点？）：**

sleep方法可以让线程进入Waiting状态，并且不占用cpu资源，但是不释放锁，直到规定时间后再执行，休眠期间如果被中断，会抛出异常并清除中断状态。

**sleep方法----常见面试问题**

**wait/notify 、sleep异同（方法属于哪个对象？线程状态怎么切换？）**

```
1.相同
    1.1 都会时线程阻塞。
    1.2 都会响应中断。
2.不同
	2.1 wait/notify 必须用在同步方法中。
    2.2 执行wait会释放锁、而sleep不会。
    2.3 sleep需要指定时间、wait可以不用。
    2.4 sleep 在 Thread，wait/notify 在 Object。wait/notify属于锁级别的操作，Java认为每个对象都可以是一把锁，而且每个对象的对象头中都会保存锁的状态。因为Object是所有类的父类。如果定义在Thread中就无法实现一个线程持有多把锁的场景。
```

**wait和sleep方法的异同（你知道wait和sleep方法的异同吗）？**

```
相同
1. Wait和sleep方法都可以使线程阻塞，对应线程状态是Waiting或Timed_Waiting。
2. wait和sleep方法都可以响应中断Thread.interrupt()。

不同
1. wait方法的执行必须在同步方法中进行，而sleep则不需要。
2. 在同步方法里执行sleep方法时，不会释放monitor锁，但是wait方法会释放monitor锁。
3. sleep方法短暂休眠之后会主动退出阻塞，而没有指定时间的wait方法则需要被其他线程中断后才能退出阻塞。
4. wait()和notify(),notifyAll()是Object类的方法，sleep()和yield()是Thread类的方法
```



#### 5. join方法详解

##### 作用：

​	因为新的线程加入了我们，所以我们需要等他执行完再出发。

##### 用法：

​	main等待thread1执行完毕。注意是：主线程等待子线程执行完，主线程再执行。

##### 三个例子：

       1.  普通用法：Demo11_Join
       2.  响应中断：Demo11_JoinInterrupt
       3.  在join期间线程的状态：Waiting

**对比 CountDownLatch 或 CyclicBarrier：**

**原理：**join 源码 native 层，线程执行后自动notifyAll()

**常见面试问题：**

​	在join期间线程会处于哪种状态 ? Waiting

#### 6. yield方法详解

**作用：**

​	释放我的cpu时间片。

**定位：**

​	JVM不保证遵循。

yield和sleep区别，是否随时可能再次被调度。



#### 7. 获取当前执行线程的引用

​	Thread.currentThread()方法



#### 8. start() 和 run()。



#### 9. stop、suspend、resume 方法。



#### 面试常见问题：

1.**两个线程交替打印0~100的奇偶数**？

​		基本思路：synchronized

​		更好的方法：wait/notify

2.手写生产者消费者设计模式？

3.**为什么wait()需要再同步代码块内使用，而sleep()不需要**？

​		a.为了让通信变得可靠，防止死锁和永久等待的发生。

​		b.防止在(第一个线程)执行wait()前，(在第二个线程中)提前执行notify(),导致执行了wait()的线程无人唤醒。

​		c.sleep()只针对本线程，不影响其他线程，也不会造成死锁。

​		d.**为什么线程通信的方法wait(),notifi()和notifyAll()被定义再object类里？而sleep定义在Thread类里**？

​			wait(),notifi()和notifyAll() 为锁级别的操作，而锁是属于某个对象的，每一个对象的对象头中都会含有几位保存当前锁的状态。所以这个锁实际上是绑定到

某个对象中而非线程。如果把 wait(),notifi()和notifyAll() 定义在线程中，会造成很大的局限性，无法实现一个线程持有多把锁的逻辑。

​		e.wait() 方法是属于Object对象的，那调用Thread.wait会怎么样？

​		f.如何选择用notify还是notifyAll？

​		g.notifyAll 之后所有线程都会再次抢夺锁，如果某线程抢夺失败怎么办？

​				等待这把锁的释放，再继续抢夺。

​		h.用suspend()和resume()来阻塞线程可以吗？为什么？

​				用suspend()、resume()由于安全功能已被废弃，推荐使用wait、notify