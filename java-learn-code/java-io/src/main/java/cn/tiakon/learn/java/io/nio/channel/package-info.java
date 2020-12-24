package cn.tiakon.learn.java.io.nio.channel;

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
