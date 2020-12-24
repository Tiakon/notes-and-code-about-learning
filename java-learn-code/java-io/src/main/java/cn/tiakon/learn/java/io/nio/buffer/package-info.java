package cn.tiakon.learn.java.io.nio.buffer;

/*
 *
 * Selector 、 Channel 和 Buffer 的关系图(简单版)
 * 关系图的说明:
 * 每个channel 都会对应一个Buffer
 * Selector 对应一个线程， 一个线程对应多个channel(连接)
 * 该图反应了有三个channel 注册到 该selector //程序
 * 程序切换到哪个channel 是有事件决定的, Event 就是一个重要的概念
 * Selector 会根据不同的事件，在各个通道上切换
 * Buffer 就是一个内存块 ， 底层是有一个数组
 * 数据的读取写入是通过Buffer, 这个和BIO , BIO 中要么是输入流，或者是  输出流, 不能双向，但是NIO的Buffer 是可以读也可以写, 需要 flip 方法切换
 * channel 是双向的, 可以返回底层操作系统的情况, 比如Linux ， 底层的操作系统 通道就是双向的.
 *
 *
 * */

/*
 * Capacity:容量，即可以容纳的最大数据量；在缓冲区创建时被设定并且不能改变
 *
 * Limit:表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改的
 *
 * Position:位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变改值，为下次读写作准备
 *
 * Mark:标记
 *
 * */


/*
 *
 *
 * public abstract class Buffer {
 *     //JDK1.4时，引入的api
 *     public final int capacity( )//返回此缓冲区的容量
 *     public final int position( )//返回此缓冲区的位置
 *     public final Buffer position (int newPositio)//设置此缓冲区的位置
 *     public final int limit( )//返回此缓冲区的限制
 *     public final Buffer limit (int newLimit)//设置此缓冲区的限制
 *     public final Buffer mark( )//在此缓冲区的位置设置标记
 *     public final Buffer reset( )//将此缓冲区的位置重置为以前标记的位置
 *     public final Buffer clear( )//清除此缓冲区, 即将各个标记恢复到初始状态，但是数据并没有真正擦除, 后面操作会覆盖
 *     public final Buffer flip( )//反转此缓冲区
 *     public final Buffer rewind( )//重绕此缓冲区
 *     public final int remaining( )//返回当前位置与限制之间的元素数
 *     public final boolean hasRemaining( )//告知在当前位置和限制之间是否有元素
 *     public abstract boolean isReadOnly( );//告知此缓冲区是否为只读缓冲区
 *
 *     //JDK1.6时引入的api
 *     public abstract boolean hasArray();//告知此缓冲区是否具有可访问的底层实现数组
 *     public abstract Object array();//返回此缓冲区的底层实现数组
 *     public abstract int arrayOffset();//返回此缓冲区的底层实现数组中第一个缓冲区元素的偏移量
 *     public abstract boolean isDirect();//告知此缓冲区是否为直接缓冲区
 * }
 *
 * */
