
 Selector 、 Channel 和 Buffer 的关系图(简单版)
 关系图的说明:
 每个channel 都会对应一个Buffer
 Selector 对应一个线程， 一个线程对应多个channel(连接)
 该图反应了有三个channel 注册到 该selector //程序
 程序切换到哪个channel 是有事件决定的, Event 就是一个重要的概念
 Selector 会根据不同的事件，在各个通道上切换
 Buffer 就是一个内存块 ， 底层是有一个数组
 数据的读取写入是通过Buffer, 这个和BIO , BIO 中要么是输入流，或者是  输出流, 不能双向，但是NIO的Buffer 是可以读也可以写, 需要 flip 方法切换
 channel 是双向的, 可以返回底层操作系统的情况, 比如Linux ， 底层的操作系统 通道就是双向的.


 Capacity:容量，即可以容纳的最大数据量；在缓冲区创建时被设定并且不能改变

 Limit:表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改的

 Position:位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变改值，为下次读写作准备

 Mark:标记

 public abstract class Buffer {
     //JDK1.4时，引入的api
     public final int capacity( )//返回此缓冲区的容量
     public final int position( )//返回此缓冲区的位置
     public final Buffer position (int newPositio)//设置此缓冲区的位置
     public final int limit( )//返回此缓冲区的限制
     public final Buffer limit (int newLimit)//设置此缓冲区的限制
     public final Buffer mark( )//在此缓冲区的位置设置标记
     public final Buffer reset( )//将此缓冲区的位置重置为以前标记的位置
     public final Buffer clear( )//清除此缓冲区, 即将各个标记恢复到初始状态，但是数据并没有真正擦除, 后面操作会覆盖
     public final Buffer flip( )//反转此缓冲区
     public final Buffer rewind( )//重绕此缓冲区
     public final int remaining( )//返回当前位置与限制之间的元素数
     public final boolean hasRemaining( )//告知在当前位置和限制之间是否有元素
     public abstract boolean isReadOnly( );//告知此缓冲区是否为只读缓冲区

     //JDK1.6时引入的api
     public abstract boolean hasArray();//告知此缓冲区是否具有可访问的底层实现数组
     public abstract Object array();//返回此缓冲区的底层实现数组
     public abstract int arrayOffset();//返回此缓冲区的底层实现数组中第一个缓冲区元素的偏移量
     public abstract boolean isDirect();//告知此缓冲区是否为直接缓冲区
 }



/*
 * 关于Buffer 和 Channel的注意事项和细节
 * ByteBuffer 支持类型化的put 和 get, put 放入的是什么数据类型，get就应该使用相应的数据类型来取出，否则可能有 BufferUnderflowException 异常。
 * 可以将一个普通Buffer 转成只读Buffer
 * NIO 还提供了 MappedByteBuffer， 可以让文件直接在内存（堆外的内存）中进行修改， 而如何同步到文件由NIO 来完成.
 * 前面我们讲的读写操作，都是通过一个Buffer 完成的，NIO 还支持 通过多个Buffer (即 Buffer 数组) 完成读写操作，即 Scattering 和 Gathering
 */

/*
 * ServerSocketChannel 在服务器端监听新的客户端 Socket 连接
 * public abstract class ServerSocketChannel      extends AbstractSelectableChannel     implements NetworkChannel{
 * public static ServerSocketChannel open()，得到一个 ServerSocketChannel 通道
 * public final ServerSocketChannel bind(SocketAddress local)，设置服务器端端口号
 * public final SelectableChannel configureBlocking(boolean block)，设置阻塞或非阻塞模式，取值 false 表示采用非阻塞模式
 * public SocketChannel accept()，接受一个连接，返回代表这个连接的通道对象
 * public final SelectionKey register(Selector sel, int ops)，注册一个选择器并设置监听事件
 * }
 * <p>
 * SocketChannel，网络 IO 通道，具体负责进行读写操作。NIO 把缓冲区的数据写入通道，或者把通道里的数据读到缓冲区。
 * <p>
 * SocketChannel，网络 IO 通道，具体负责进行读写操作。NIO 把缓冲区的数据写入通道，或者把通道里的数据读到缓冲区。
 * public abstract class SocketChannel      extends AbstractSelectableChannel     implements ByteChannel, ScatteringByteChannel, GatheringByteChannel, NetworkChannel{
 * public static SocketChannel open();//得到一个 SocketChannel 通道
 * public final SelectableChannel configureBlocking(boolean block);//设置阻塞或非阻塞模式，取值 false 表示采用非阻塞模式
 * public boolean connect(SocketAddress remote);//连接服务器
 * public boolean finishConnect();//如果上面的方法连接失败，接下来就要通过该方法完成连接操作
 * public int write(ByteBuffer src);//往通道里写数据
 * public int read(ByteBuffer dst);//从通道里读数据
 * public final SelectionKey register(Selector sel, int ops, Object att);//注册一个选择器并设置监听事件，最后一个参数可以设置共享数据
 * public final void close();//关闭通道
 * }
 */

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
 *  public abstract Selector selector();//得到与之关联的 Selector 对象
 *  public abstract SelectableChannel channel();//得到与之关联的通道
 *  public final Object attachment();//得到与之关联的共享数据
 *  public abstract SelectionKey interestOps(int ops);//设置或改变监听事件
 *  public final boolean isAcceptable();//是否可以 accept
 *  public final boolean isReadable();//是否可以读
 *  public final boolean isWritable();//是否可以写
 * }
 */