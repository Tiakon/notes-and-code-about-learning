package cn.tiakon.learn.java.io.nio.kafka;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ClientNetwork {

    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            Socket socket = socketChannel.socket();

            socket.setKeepAlive(true);
            socket.setReceiveBufferSize(1024 * 8);
            socket.setSendBufferSize(1024 * 8);
            socket.setTcpNoDelay(true);

            Selector selector = Selector.open();
            boolean connect = socketChannel.connect(new InetSocketAddress(9092));
            System.out.println("连接状态: " + connect);
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                int select = selector.select(5000);
                System.out.println("监听到的事件数：" + select);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                for (SelectionKey selectionKey : selectionKeys) {

                    if (selectionKey.isValid() && selectionKey.isConnectable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        if (channel.finishConnect()) {
                            System.out.println(" 与 " + channel.getRemoteAddress().toString() + " 完成连接建立... ");

                            //取消了OP_CONNECT事件 增加了OP_READ 事件
                            selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

                            selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);

                            ByteBuffer writeByteBuffer = ByteBuffer.allocate(64);
                            writeByteBuffer.clear();
                            writeByteBuffer.put(StandardCharsets.UTF_8.encode("hello world !!!"));
                            writeByteBuffer.flip();
                            while (writeByteBuffer.hasRemaining()) {
                                channel.write(writeByteBuffer);
                            }

                            selectionKey.interestOps(selectionKey.interestOps() & ~SelectionKey.OP_WRITE);
                            // 为什么添加这行代码后 select又 恢复成阻塞状态？？？
//                            channel.register(selector, SelectionKey.OP_READ);
                        }
                    }

                    if (selectionKey.isValid() && selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
                        byteBuffer.clear();
                        while (channel.read(byteBuffer) > 0) ;
                        byteBuffer.flip();
                        String message = String.valueOf(StandardCharsets.UTF_8.decode(byteBuffer));
                        System.out.println(message);
                    }

                }

                selectionKeys.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
