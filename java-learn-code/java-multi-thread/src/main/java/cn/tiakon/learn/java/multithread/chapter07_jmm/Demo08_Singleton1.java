package cn.tiakon.learn.java.multithread.chapter07_jmm;

public class Demo08_Singleton1 {
    public static void main(String[] args) {

    }
}

/**
 * 饿汉：在类加载时就初始化变量。
 * 1.饿汉式（静态常量）可用
 * 优点： 写法简单; jvm做类加载时就初始化了静态变量(即完成了INSTANCE实例)，避免了线程同步问题
 * <p>
 * last time   : 2020/10/30 12:54
 *
 * @author tiankai.me@gmail.com on 2020/10/30 12:54.
 */
class Singleton1 {
    private final static Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}

/**
 * 2.饿汉式(静态代码块) 可用
 * 优点： 写法简单; jvm做类加载时就初始化了静态变量(即完成了INSTANCE实例)，避免了线程同步问题
 * last time   : 2020/10/30 12:54
 *
 * @author tiankai.me@gmail.com on 2020/10/30 12:54.
 */
class Singleton2 {
    private final static Singleton2 INSTANCE;

    static {
        INSTANCE = new Singleton2();
    }

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return INSTANCE;
    }
}

/**
 * 懒汉：在方法被调用时，再初始化变量。节约了内存空间。
 * 3. 懒汉式（线程不安全）【不可用】
 * last time   : 2020/10/30 13:07
 *
 * @author tiankai.me@gmail.com on 2020/10/30 13:07.
 */
class Singleton3 {
    private static Singleton3 INSTANCE;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }
}

/**
 * 4.懒汉式（线程安全，同步方法）【不推荐用】
 * 缺点：多线程处理时效率低下。
 * <p>
 * last time   : 2020/10/30 13:11
 *
 * @author tiankai.me@gmail.com on 2020/10/30 13:11.
 */
class Singleton4 {
    private static Singleton4 INSTANCE;

    private Singleton4() {
    }

    public synchronized static Singleton4 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton4();
        }
        return INSTANCE;
    }
}

/**
 * 5.懒汉式（线程不安全，同步代码块）【不可用】
 * last time   : 2020/10/30 13:15
 *
 * @author tiankai.me@gmail.com on 2020/10/30 13:15.
 */
class Singleton5 {
    private static Singleton5 INSTANCE;

    private Singleton5() {
    }

    public synchronized static Singleton5 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton5.class) {
                INSTANCE = new Singleton5();
            }
        }
        return INSTANCE;
    }
}

/**
 * 6.懒汉式（线程安全，同步方法，双重检查）【推荐面试使用】
 * 优点：线程安全；延迟加载；效率较高。
 * 面试问题？
 * 1. 为什么要double-check
 * 为了线程安全。
 * 2.使用同步方法，单check行不行？
 * 行，但是有性能问题。
 * 为什么要用volatile
 * 1. 创建对象是一个非原子操作(实际上有3个步骤)
 * 2. 重排序会带来NPE
 * 3. 防止重排序
 * <p>
 * <p>
 * last time   : 2020/10/30 13:15
 *
 * @author tiankai.me@gmail.com on 2020/10/30 13:15.
 */
class Singleton6 {
    private static volatile Singleton6 INSTANCE;

    private Singleton6() {
    }

    // A线程与B线程同时运行 A先创建对象发生：

    // JVM和cpu优化，发生了指令重排

    // 1、construct empty Singleton6 创建空对象。
    // 3、assign to INSTANCE 分配实例给对象。
    // 2、call constructor 调用构造方法，初始化对象。

    // B线程可能会得到空指针异常（NPE）而出错

    public synchronized static Singleton6 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton5.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton6();
                    // 1、construct empty Singleton6 创建空对象。
                    // 2、call constructor 调用构造方法，实例化对象。
                    // 3、assign to INSTANCE 分配实例给对象。
                }
            }
        }
        return INSTANCE;
    }
}

/**
 * 7. 懒汉式（线程安全，懒加载）
 * 静态内部类。
 * <p>
 * last time   : 2020/10/31 9:43
 *
 * @author tiankai.me@gmail.com on 2020/10/31 9:43.
 */
class Singleton7 {
    private static volatile Singleton6 INSTANCE;

    private Singleton7() {
    }

    // 加载外部类，不会加载内部类；jvm保证，多线程访问，也不会创建多个对象。
    private static class SingletonInstance {
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance() {
        return SingletonInstance.INSTANCE;
    }
}

/**
 * 8. 懒汉式（线程安全，懒加载）
 * 枚举单例。
 * <p>
 * last time   : 2020/10/31 9:43
 *
 * @author tiankai.me@gmail.com on 2020/10/31 9:43.
 */
enum Singleton8 {
    INSTANCE;

    public void whatever() {

    }
}