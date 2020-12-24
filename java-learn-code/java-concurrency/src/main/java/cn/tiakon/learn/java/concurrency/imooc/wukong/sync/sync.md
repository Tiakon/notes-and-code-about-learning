
# Synchronized

### 1、Synchronized 介绍

###### Synchronized 的作用

    1.同步方法支持一种简单的策略来防止线程干扰和内存一致性错误：如果一个对象对多个线程可见，
    则对该对象变量的所有读取或写入都是通过同步方法完成的。
    
    2.能够保证在同一时刻最多只有一个线程执行该段代码，以达到保证并发安全的效果。

###### Synchronized 的地位

    1.Synchronized是java的关键字
    2.是最基本的互斥同步手段

###### 不控制并发的后果 

![01-不使用并发手段会有什么后果](R:\code\project-source\notes-and-code-about-learning\java-learn-code\java-concurrency\src\main\java\cn\tiakon\learn\java\concurrency\imooc\wukong\sync\01-不使用并发手段会有什么后果.png)



### 2、Synchronized的两种用法

    对象锁：包括方法锁（默认锁对象为this当前实例对象）和同步代码块锁（自己指定锁对象）。
    类锁：指synchronized修饰静态的方法（静态修饰的对象变量）或指定锁为Class对象。

###### 第一个用法：对象锁：

    1.代码块形式：手动指定锁对象。
    2.方法锁形式：synchronized修饰普通方法，锁对象默认为this。

###### 第二个用法：类锁：

    指synchronized修饰静态的方法或指定锁为Class对象。
    
    概念：
        只有一个Class对象：Java类可能会有很多对象，但是只有1个Class对象。
        本质：所以所谓的类锁，不过是Class对象的锁而已。
        用法和效果：类锁只能在同一时刻被一个对象拥有。
       
    两种形式： 
        形式1：synchronized 加在static方法上
        形式2：synchronized(*.class) 代码块

### 3、多线程访问同步方法的7种情况（面试常考）

     1.两个线程同时访问一个对象的同步方法。
        串行
     2.两个线程同时访问两个对象的同步方法。
        并行
     3.两个线程同时访问的是synchronized静态方法。
        串行
     
     4.同时访问同步方法与非同步方法。 
        一个对象时，并行。
        两个对象时，并行。
     
     5.访问同一个对象的不同的普通同步方法。
        串行
     
     6.同时访问静态synchronized和非静态synchronized方法。
        一个对象时，并行。
        两个对象时，并行。
          this对象与Class对象不一样。
        
     7.方法抛异常后，Jvm帮我们释放锁。
     
     总结7种情况3点核心思想：
     
        1. 一把锁只能同时被一个线程获取，没有拿到锁的线程必须等待（对应1、5情况）
        
        2. 每个实例都对应有自己的一把锁，不同实例之间互不影响；
            
            例外：锁对象是*.class 以及 synchronized 修饰的是static方法的时候，所有对象共用同意吧类锁（对应第2、3、4、6种情况）；
        
        3. 无论是方法正常执行完毕或者方法抛出异常，都是释放锁（对应第7中情况）。
    
     注意：目前进入一个被synchronized修饰的方法，方法里去调用没有被synchronized修饰的方法，这个时候还是线程安全的吗？不是。



### 4、 Synchronized 性质

    可重入性（递归锁）
    
        什么是可重入：指的是同一线程的外层函数获得锁之后，内层函数可以直接再次获得该锁。 
        
                摇号是不可重入，此处是可重入。
        
        好处：避免死锁，提升封装性。
        
                避免死锁：方法1、方法2都被synchronized修饰，线程1执行到方法1获取到了锁，方法2也是synchronized方法，要执行方法2也需要拿到锁。
                        
                假设synchronized不具备可重入性，对于线程1来说，虽然拿到了方法1的锁，但是想要访问方法2，没有这把锁，不具备可重入，不能使用本身已经获取到的锁，这样一来，形成即想拿锁，又不释放锁的局面，永远等待，造成死锁
        
        粒度：线程而非调用（用3种情况来说明和pthread的区别）pthread粒度是调用。
        
                情况1：证明同一个方法是可重入的                
                情况2：证明可重入不要求是同一个方法                
                情况3：证明可重入不要求是同一个类中的                
    
    不可中断
    
        一旦这个锁被别人获得了，如果我还想获得，我只能选择等待或者阻塞，直到别的线程释放这个锁，如果别人永远不释放锁，那么我只能永远的等待下去。
        
        Lock
        拥有中断能力
        1.如果我觉得我等的时间太长了，有权中断现在已经获得锁的线程的执行
        2.如果我觉得我等的时间太长了不想再等了，也可以退出。


​        
### 5、 Synchronized  加锁和释放锁的原理
    现象：
    
        每一个类的实例对应着一把锁，而每一个synchronized方法都必须首先获得调用该方法的类的实例的锁，才能执行。否则线程就会阻塞，而方法一旦执行，就会独占这把锁，直到该方法返回，或者抛出异常，才将锁释放。释放之后，其他被阻塞的线程才能获得这把锁，重新进入可执行的状态。
        
        这就意味着当一个对象中有 synchronized 修饰方法或代码块时，要想执行这个代码就必须先获得这个对象锁，如果此对象的对象锁被其它调用者所占用，就必须等待到它被释放。所有的Java对象都含有一个互斥锁，这个锁由jvm自动获取和释放，我们只需要指定这个对象就可以。
    
    获取和释放锁的时机：内置锁（或监视器锁、monitor）线程在进入到同步代码块前会自动获取，并且退出同步代码块后或抛出异常后会自动释放。获得锁的唯一途径就是进入该锁同步的代码块中。
     
     等价代码
     
    深入JVM看字节码：反编译、monitor指令   
    
    可重入性： 加锁次数计数器  
        可重入指的是，当一个线程拿到一把锁后，想再次进入这把锁所控制的方法或代码块。它可以直接进入。
        可重入原理：加锁次数计数器
        1、JVM负责跟踪对象被加锁的次数；  
        2、有个monitor计数器，线程第一次给对象加锁的时候，计数变为1.每当这个相同的线程在此对象上再次获得锁时，计数会递增。
        3、每当任务离开时，计数递减，当计数为0的时候锁被完全释放。
        
    保证可见性的原理：内存模型
        
        可见性原理：Java内存模型
        
        synchronized如何做到可见性的呢？
        
        一旦代码块或者方法被synchronized修饰，那么在执行完毕之后，被锁住的对象所做的任何操作，都要在释放锁之前，从线程内存写回到主内存中（不会存在线程内存和主内存不一致的情况）。同样，在进入代码块得到锁之后，被锁定对象的数据也是直接从主内存中读取出来。

### 6、Synchronized三大缺陷

    1.效率低：
        
        锁的释放情况少、试图获得锁时不能设定超时时间、不能中断一个正在试图获得锁的线程。
        
            a.当线程获取到锁，在执行过程中，其他线程也想用该锁时，只能等待当前线程释放。释放情况少，体现在两种情况：执行完毕、异常(JVM将锁释放)。
            b.如果要等待IO这种耗时操作或者sleep，又不会主动释放锁，其他线程只能等着，非常影响程序执行的效率。需要一种机制让一直在等待又影响其他线程执行的这些情况得到遏制。
            c.不能设定超时时间；lock可以设置超时时间，还有中断机制。
        
    2.不过灵活：
        
        加锁和释放锁的时机单一，每个锁仅有单一的条件（锁住某个对象，别的线程只能等待锁释放）。
        读写锁相比很灵活，读的时候不加锁，写的时候加锁。
        
    3.无法知道是否成功获取到锁。
    
        lock可以尝试获取锁，成功了，做一些逻辑。没成功，做其他的逻辑。

### 7、常见面试问题

    1、使用注意点：锁对象不能为空、作用域不宜过大、避免死锁。
    2、如何选择Lock和synchronized关键字
    
        1）如果可以的话，两者都不要使用，使用JUC中的各种类
        2）如果synchronized关键字，在程序中适用，那么就优先使用（可以减少所编写的代码）
        3）如果特别需要使用到Lock独有的特性时就用。
        建议思路：
            1.避免出错，有现成的工具包就直接使用。
            2.没有的话，优先使用synchronized（减少代码编写）。
            3.如果需要灵活的加锁、释放锁就使用Lock
    3、多线程访问同步方法的各种具体情况。


### 8、思考题

    1、多个线程等待同一个synchronized锁的时候，JVM如何选择下一个获取锁的是哪个线程？
    
    1）内部锁调度机制。
    2）线程释放锁之后，竞争锁的对象有：等待中的线程、刚刚申请这把锁的线程。
    3）实现细节和JVM的版本、具体实现相关，不能依赖算法。
    
    2、synchronized使得同时只有一个线程可以执行，性能较差，有什么方法可以提升性能？
      
    1、优化使用范围
    2、使用其他类型的锁（读写锁）
    3、想灵活的控制锁的获取和释放（现在释放锁的时机都被规定死了），怎么办？自己实现一个锁
    4、什么是锁的升级、降级？什么是JVM里的偏斜锁、轻量级锁、重量级锁？
    
    什么是锁的升级、降级？
      
        所谓锁的升级、降级，就是 JVM 优化 synchronized 运行的机制，当 JVM 检测到不同的竞争状况时，会自动切换到适合的锁实现，这种切换就是锁的升级、降级。
    
    什么是JVM里的倾斜锁、轻量级锁、重量级锁？


  
### 9、总结

    1. 一句话总结 synchronized？
        
        JVM会自动通过使用monitor来加锁和解锁，保证了同时只有一个线程可以执行指定代码，从而保证了线程安全，同时具有可重入和不可中断的性质。
    
        保证被 synchronized 修饰的代码块或方法，在多线程中串行执行。