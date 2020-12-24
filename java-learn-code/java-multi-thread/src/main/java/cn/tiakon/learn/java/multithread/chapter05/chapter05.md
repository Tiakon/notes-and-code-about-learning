## 7. 未捕获异常如何处理 ？

#### 考考你

Java 异常体系图

实际工作中，如何全局处理异常？为什么要全局处理？不处理行不行？

#### 线程的未捕获异常UncacughtException 应该如何处理？

- 为什么需要 UncaughtExceptionHandler ？
  1. 主线程可以轻松发现异常，子线程却不行
  2. 子线程异常无法用传统方法捕获
  3. 不能直接捕获的后果、可以提高健壮性，统一异常捕获。
- 两种解决方案
  - 方案一（不推荐） :手动在每个run方法中进行try catch。
  - 方案二（推荐）   :利用UncaughtExceptionHandler。
    - 实现 UncaughtEcxceptionHandler 接口。
    - void uncaughtException(Thread t,Throwable e);
    - 异常处理器的调用策略。
    - 自己实现
      - 给程序统一设置
      - 给每个线程单独设置
      - 给线程池设置

#### 线程的未捕获异常——常见面试问题

- Java异常体系
- 实际工作中，如何全局处理异常？为什么要全局处理？不处理行不行？
  - 可以根据业务的需要实现UncaughtExceptionHandler接口（未捕获异常），设置线程进行统一异常捕获，分别返回经过处理后的前端异常信息与后端保存到日志中的异常信息。
- run方法是否可以抛出异常？如果抛出异常，线程的状态会怎么样？
  - 不可以，接口中没有声明。线程会中止运行并打印异常堆栈。
- 线程中如何处理某个未处理异常？
  - 实现UncaughtExceptionHandler接口，进行统一异常捕获。

