package cn.tiakon.learn.java.io.nio.learn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Example03_SocketChannelAndSelect {

    public static void main(String[] args) throws IOException {

        Thread serverSocketChannelThread = new Thread(() -> {
            try {
                useServerSocketChannelMethod();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverSocketChannelThread.start();

        useSocketChannelMethod();
    }

    public static void useSocketChannelMethod() throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端的ip 和 端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                // 因为连接需要时间，客户端不会阻塞，可以做其它工作..
                System.out.println("等待连接成功时，执行其它任务...");
            }
        }

        //...如果连接成功，就发送数据
        String str = "hello, world ~ connection is success...";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();
        //发送数据，将 buffer 数据写入 channel
        socketChannel.write(buffer);
        buffer.clear();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String nextLine = scanner.nextLine();
            System.out.println("ke客户端发送：" + nextLine);
            buffer.put(nextLine.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
    }

    public static void useServerSocketChannelMethod() throws IOException {

        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();
        //绑定一个端口6666, 在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把 serverSocketChannel 注册到  selector 关心 事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("注册后的 SelectionKey 数量=" + selector.keys().size());

        //循环等待客户端连接
        while (true) {

            // selector.select(long timeout):监控所有注册的通道，当其中有IO操作可以进行时，将对应的 SelectionKey 加入到内部集合中并返回，参数用来设置超时时间
            //这里我们等待1秒，如果没有事件发生, 返回
            if (selector.select(5000) == 0) { //没有事件发生
                System.out.println("当前无连接...");
                continue;
            }
            //  selector.selectedKeys():从内部集合中得到所有的  SelectionKey
            //如果返回的>0, 就获取到相关的 selectionKey 集合
            //1. 如果返回的>0， 表示已经获取到关注的事件
            //2. selector.selectedKeys() 返回关注事件的集合
            //   通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys 数量 = " + selectionKeys.size());

            //遍历 Set<SelectionKey>, 使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();

                // 处理连接事件
                if (key.isAcceptable()) {
                    //OP_ACCEPT:有新的客户端连接
//                    SocketChannel socketChannel = serverSocketChannel.accept();
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel1.accept();
                    System.out.println("客户端连接成功...");
                    //将 SocketChannel 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给socketChannel
                    //关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("socketChannel 注册事件 , SelectionKey 的数量：" + selector.keys().size());
                }

                // 处理可读事件
                if (key.isReadable()) {
                    //发生 OP_READ
                    //通过key 反向获取到对应channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("position:" + buffer.position());
                    System.out.println("limit:" + buffer.limit());
                    buffer.flip();
                    System.out.println("position:" + buffer.position());
                    System.out.println("limit:" + buffer.limit());
                    byte[] bytes = new byte[buffer.limit()];
                    // 截取字节缓存
                    buffer.get(bytes, buffer.position(), buffer.limit());
                    String recvMessage = new String(bytes);
                    System.out.println("form 客户端: ".concat(recvMessage));
                    buffer.clear();
                }

                //手动从集合中移动当前的selectionKey, 防止重复操作
                keyIterator.remove();
            }

        }

    }

}
