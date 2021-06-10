package cn.tiakon.learn.java.io.nio.learn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

// selector.keys() 与 selector.selectedKeys() 的区别？
// selector.keys() : 返回此选择器的key集合.selected-key不可直接修改。 仅在取消selected-key并取消注册其通道后，才能将其删除。 任何试图修改selected-key的尝试都将引发UnsupportedOperationException。selected-key不是线程安全的
// selector.selectedKeys(): 返回此选择器的选择键集。可以从所选键集中删除键，但不能直接将其添加到所选键集中。 任何将对象添加到键集中的尝试都将引发UnsupportedOperationException 。 所选键集不是线程安全的
public class Example04_Selector {

    private static Selector selector;

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                selector.wakeup();
                Set<SelectionKey> keys = selector.keys();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                System.out.println("执行 selector.wakeup() 之后 ， selector的信息");
                System.out.println("keys.size() = " + keys.size());
                System.out.println("selectedKeys.size() =  " + selectedKeys.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            int select = selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectedKeys.iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey key = selectionKeyIterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                    Socket socket = serverSocketChannel1.socket().accept();
                    socket.close();
                }
                selectionKeyIterator.remove();
            }
            serverSocketChannel.close();
            System.out.println("main end!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
