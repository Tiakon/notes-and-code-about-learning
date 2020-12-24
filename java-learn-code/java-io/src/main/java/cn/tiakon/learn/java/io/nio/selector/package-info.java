package cn.tiakon.learn.java.io.nio.selector;

/*
 * Selector(选择器)
 * <p>
 * 基本介绍
 * Java 的 NIO，用非阻塞的 IO 方式。可以用一个线程，处理多个的客户端连接，就会使用到Selector(选择器)
 * <p>
 * Selector 能够检测多个注册的通道上是否有事件发生(注意:多个Channel以事件的方式可以注册到同一个Selector)，如果有事件发生，便获取事件然后针对每个事件进行相应的处理。这样就可以只用一个单线程去管理多个通道，也就是管理多个连接和请求。【示意图】
 * <p>
 * <p>
 * 只有在 连接/通道 真正有读写事件发生时，才会进行读写，就大大地减少了系统开销，并且不必为每个连接都创建一个线程，不用去维护多个线程
 * 避免了多线程之间的上下文切换导致的开销
 * <p>
 * <p>
 * <p>
 * 特点再说明:
 * Netty 的 IO 线程 NioEventLoop 聚合了 Selector(选择器，也叫多路复用器)，可以同时并发处理成百上千个客户端连接。
 * 当线程从某客户端 Socket 通道进行读写数据时，若没有数据可用时，该线程可以进行其他任务。
 * 线程通常将非阻塞 IO 的空闲时间用于在其他通道上执行 IO 操作，所以单独的线程可以管理多个输入和输出通道。
 * 由于读写操作都是非阻塞的，这就可以充分提升 IO 线程的运行效率，避免由于频繁 I/O 阻塞导致的线程挂起。
 * 一个 I/O 线程可以并发处理 N 个客户端连接和读写操作，这从根本上解决了传统同步阻塞 I/O 一连接一线程模型，架构的性能、弹性伸缩能力和可靠性都得到了极大的提升。
 * <p>
 * <p>
 * public abstract class Selector implements Closeable {
 * public static Selector open();//得到一个选择器对象
 * public int select(long timeout);//监控所有注册的通道，当其中有 IO 操作可以进行时，将
 * 对应的 SelectionKey 加入到内部集合中并返回，参数用来设置超时时间
 * public Set<SelectionKey> selectedKeys();//从内部集合中得到所有的 SelectionKey
 * }
 * <p>
 * <p>
 * 注意事项
 * <p>
 * NIO中的 ServerSocketChannel功能类似ServerSocket，SocketChannel功能类似Socket
 * <p>
 * selector 相关方法说明
 * <p>
 * selector.select()//阻塞
 * selector.select(1000);//阻塞1000毫秒，在1000毫秒后返回
 * selector.wakeup();//唤醒selector
 * selector.selectNow();//不阻塞，立马返还
 * <p>
 * <p>
 * SelectionKey
 * <p>
 * SelectionKey，表示 Selector 和网络通道的注册关系, 共四种:
 * int OP_ACCEPT：有新的网络连接可以 accept，值为 16
 * int OP_CONNECT：代表连接已经建立，值为 8
 * int OP_READ：代表读操作，值为 1
 * int OP_WRITE：代表写操作，值为 4
 * 源码中：
 * public static final int OP_READ = 1 << 0;
 * public static final int OP_WRITE = 1 << 2;
 * public static final int OP_CONNECT = 1 << 3;
 * public static final int OP_ACCEPT = 1 << 4;
 * <p>
 * <p>
 * <p>
 * public abstract class SelectionKey {
 *  public abstract Selector selector();//得到与之关联的 Selector 对象
 *  public abstract SelectableChannel channel();//得到与之关联的通道
 *  public final Object attachment();//得到与之关联的共享数据
 *  public abstract SelectionKey interestOps(int ops);//设置或改变监听事件
 *  public final boolean isAcceptable();//是否可以 accept
 *  public final boolean isReadable();//是否可以读
 *  public final boolean isWritable();//是否可以写
 * }
 */