package cn.tiakon.learn.java.concurrency.imooc.jimin.threadpool;


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Administrator
 */
public class RejectedExecutionDemo {

    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);
        // 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(15);
        // 缓冲队列200：用来缓冲执行任务的队列
        executor.setQueueCapacity(200);
        // 允许线程的空闲时间60秒：当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀：设置好了之后可以方便定位处理任务所在的线程池
        executor.setThreadNamePrefix("taskExecutor-");
                /*
      线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，
      当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；
      如果执行程序已关闭，则会丢弃该任务
       */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // ‘设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // ’设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(600);
        return executor;
    }

    public static void main(String[] args) {

        int corePoolSize = 10;
        int maxPoolSize = 10;
        long keepAliveTime = 0;

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(100);

        // 拒绝策略1：将抛出 RejectedExecutionException.
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        // 拒绝策略2： 用于被拒绝任务的处理程序，它直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务。
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        // 拒绝策略3：抛弃队列里最近的一个任务，处理当前任务
        // RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

        // 拒绝策略4: 用于被拒绝任务的处理程序，默认情况下它将丢弃被拒绝的任务。
//        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue, handler);

        Worker worker = new Worker();
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executor.execute(worker);
        }

        executor.shutdown();

        System.out.println(System.currentTimeMillis() - currentTimeMillis);


    }
}

class Worker implements Runnable {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run() {
        int i = atomicInteger.incrementAndGet();
        System.out.println(Thread.currentThread().getName() + " is running " + i);
    }

}