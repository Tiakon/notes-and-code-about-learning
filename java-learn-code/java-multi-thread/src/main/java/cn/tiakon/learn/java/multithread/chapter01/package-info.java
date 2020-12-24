package cn.tiakon.learn.java.multithread.chapter01;


/**
 * https://docs.oracle.com/javase/8/docs/api/
 * Oracle 官网中写道：创建线程的方式有两种：
 * <p>
 * 1. 声明实现Runnable接口的类。然后该类实现run方法（推荐）。 {@link cn.tiakon.learn.java.multithread.chapter01.RunableStyle}
 * 2. 将一个类声明为Thread的子类。这个子类应该重写Thread类的run方法。 {@link cn.tiakon.learn.java.multithread.chapter01.ThreadStyle}
 * <p>
 * 使用 Runanle 方式的优点：
 * <p>
 * 1. 使 run()方法中的逻辑代码与线程的创建分离，实现了代码的解耦。
 * 2. 可以利用线程池，避免重复创建线程所带来的的开销。
 * 3. 可扩展性强，java不支持多继承，支持多接口的实现。
 * <p>
 * <p>
 * 总结：
 * <p>
 * 准确的讲，创建线程只有一种方式那就是构造Thread类，而实现线程的执行单元有两种方式:
 * <p>
 * 方式一：实现 Runnable 接口的 run 方法，并把 Runnable 实例传递给 Thread 类。
 * 方式二：继承 Thread 类，在子类中重写 run 方法。
 * <p>
 * <p>
 * 典型错误观点分析:
 * 1. “线程池创建线程也算是一种新创建线程的方式”
 * 分析：通过线程池创建的线程也是通过Thread类创建的。
 * 2. "通过Callable和FutureTask创建线程，也算是一种新建线程的方式"
 * 分析：
 * 3. “无返回值是实现 Runnable 接口，有返回值是实现 Callable 接口，所以Callable是新的实现线程的方式。”
 * <p>
 * 4. “定时器创建线程”
 * <p>
 * 5. “匿名内部类”
 * <p>
 * 6. “Lambda表达式”
 * <p>
 * 有多少种实现线程的方法？思路有5点：
 * 1. 从不同的角度看，会有不同的答案。
 * 2. 典型答案是两种。
 * 3. 我们看原理，两种本质都是一样的。
 * 4. 具体展开说其它方式。
 * 5. 结论。
 * <p>
 * 有多少种实现多线程的方式？
 * 答题思路，有以下5点：
 * 1. 从不同的角度看，会有不同的答案。
 * 2. 典型答案是两种，分别是实现Runnable接口和继承Thread类，然后具体展开
 * 说；
 * 3. 但是，我们看原理，其实Thread类实现了Runnable接口，并且看Thread类的
 * run方法，会发现其实那两种本质都是一样的，run方法的代码如下：
 * 方法一和方法二，也就是“继承Thread类然后重写run()”和“实现Runnable接口并传入
 * Thread类”在实现多线程的本质上，并没有区别，都是最终调用了start()方法来新建
 * 线程。这两个方法的最主要区别在于run()方法的内容来源：
 * 方法一：最终调用target.run();
 * 方法二：run()整个都被重写
 * 4. 然后具体展开说其他方式；
 * 还有其他的实现线程的方法，例如线程池等，它们也能新建线程，但是细看源
 * 码，从没有逃出过本质，也就是实现Runnable接口和继承Thread类。
 * 5. 结论:我们只能通过新建Thread类这一种方式来创建线程，但是类里面的run方法
 * 有两种方式来实现，第一种是重写run方法，第二种实现Runnable接口的run方
 * 法，然后再把该runnable实例传给Thread类。除此之外，从表面上看线程池、
 * 定时器等工具类也可以创建线程，但是它们的本质都逃不出刚才所说的范围。
 * 以上这种描述比直接回答一种、两种、多种都更准确。
 */


