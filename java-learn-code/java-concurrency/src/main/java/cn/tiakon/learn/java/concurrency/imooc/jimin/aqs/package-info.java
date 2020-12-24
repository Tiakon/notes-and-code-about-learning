package cn.tiakon.learn.java.concurrency.imooc.jimin.aqs;


/**
 * AQS：
 *
 *
 *
 * 同步组件：
 *
 * CountDownLanch：
 * 初始化一定大小的计数器，线程1调用countDownLatch.await();后会处于阻塞状态，其它线程调用countDownLatch.countDown();时，计数器会减1。计数器为0时，线程1则继续执行。
 * 计数器等于0时，只会出现一次，此计数器不会被重置。
 *
 * Semaphore:信号量，做并发访问次数。
 * 控制某个资源同时被访问的个数。
 * 提供有限的访问资源。
 *
 * CyclicBarier：同步辅助类
 * 允许一组线程相互等待，直到到达某个点。当各个线程都准备就绪后，各个线程才能继续后续的操作。
 * 计数器执行加一操作。可循环重用。
 *
 *
 * CountDownLanch 与 CyclicBarier的区别：
 * 1. CountDownLanch 只能时候用一次。CyclicBarier可通过reset()重试，循环使用。
 * 2. CountDownLanch 实现一个或多个线程，需要等待其它线程完成某项操作后，才能继续往下执行（一个或多个线程等待其它线程的关系）。
 *    CyclicBarier 实现了多个线程之间相互等待，直到都满足某个条件后，再继续之后的操作（各个线程内部相互等待的关系 ）。
 */




/**
 *
 *
 * Callable 与 Runnable 接口对比
 *
 * Callable接口中的方法 call() 具有返回值。
 *
 * Callable : 可以再任务执行完后得到任务的结果。
 *
 * Future 接口：可以获取线程任务的返回值
 *  1.可以对任务取消
 *  2.查询任务是否被取消
 *  3.查询任务是否完成
 *  4.调用 get() 获取结果任务没有完成时，会阻塞，直到任务完成。
 *
 * FutureTask 类：的父类是 RunnableFuture ，RunnableFuture继承了 Runnable、Future接口，
 * 既可以被当做 Runnable 被 Thread执行，也可以获取线程的结果。
 * 应用场景：需要返回值，但又不是立刻需要。
 *
 * */



/**
 *
 * Fork/Join 框架：Java7 提供的用于并行执行任务的框架。
 * 将大任务分割成若干个小任务，最终汇总每个小任务结果后，得到大任务结果的框架。
 *
 * 采用工作窃取算法。
 * 线程1 ->(头) 队列1(双端队列) -> done
 * 线程2 ->(头) 队列2(双端队列) (尾)<- 线程1
 *
 * 优点：
 * 充分利用线程进行并行计算，减少了线程间的竞争。
 *
 * 缺点:
 * 当双端队列中只有一个任务时，线程间还是会出现竞争，同时还消耗了系统资源，
 * 创建多个线程和多个双端队列。
 *
 * */