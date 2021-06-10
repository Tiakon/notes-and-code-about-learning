package cn.tiakon.learn.java.io.nio.learn;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Example05_Selector2 {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            SocketChannel socketChannel1 = SocketChannel.open();
            socketChannel1.configureBlocking(false);

            SocketChannel socketChannel2 = SocketChannel.open();
            socketChannel2.configureBlocking(false);

            Selector selector = Selector.open();

            SelectionKey key1 = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 16

            SelectionKey key2 = socketChannel1.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);  // 9

            SelectionKey key3 = socketChannel2.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE); // 13

            int interestSet = key1.interestOps();
            boolean isInterestedInAccept = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
            boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
            boolean isInterestedInRead = (interestSet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
            boolean isInterestedInWrite = (interestSet & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
            // 可以看到，用“位与”操作interest 集合和给定的SelectionKey常量，可以确定某个确定的事件是否在interest 集合中。

            System.out.println("isInterestedInAccept: " + isInterestedInAccept);
            System.out.println("isInterestedInConnect: " + isInterestedInConnect);
            System.out.println("isInterestedInRead: " + isInterestedInRead);
            System.out.println("isInterestedInWrite: " + isInterestedInWrite);

            /**
             *
             * 位逻辑运算符：
             * & :位与运算符，只有两个操作数都是true，结果才是true。
             * | :位或运算符，只有两个操作数都是false，结果才是false。
             * ~ :位非运算符：如果位为0，结果是1，如果位为1，结果是0.
             * ^ :位异或运算：两个数转为二进制，然后从高位开始比较，如果相同则为0，不相同则为1。
             * 位同或运算：两个数转为二进制，然后从高位开始比较，如果相同则为1，不相同则为0。java中并没有同或运算符，可以通过异或转换得到。同或运算 = 异或运算  ^  1
             *
             * 逻辑运算符：
             * && :逻辑与运算，也是只有两个操作数都是true，结果才是true。但是如果左边操作数为false，就不计算右边的表达式，直接得出false。类似于短路了右边。
             * || :逻辑或运算，也是只有两个操作数都是false，结果才是false。但是如果左边操作数为true，就不计算右边的表达式，直接得出true。类似于短路了右边。
             * !  :逻辑非运算，对操作数取反。
             *
             * */

            System.out.println(SelectionKey.OP_ACCEPT);                                                      // 16
            System.out.println(SelectionKey.OP_CONNECT | SelectionKey.OP_READ);                              // 9
            System.out.println(SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);      // 13
            System.out.println("");

            System.out.println(~key1.interestOps() & SelectionKey.OP_ACCEPT);           //  0
            System.out.println(~key1.interestOps() & SelectionKey.OP_CONNECT);          //  8
            System.out.println(~key1.interestOps() & SelectionKey.OP_READ);             //  1
            System.out.println(~key1.interestOps() & SelectionKey.OP_WRITE);            //  4
            System.out.println("");

            System.out.println(~key2.interestOps() & SelectionKey.OP_ACCEPT);           //  16
            System.out.println(~key2.interestOps() & SelectionKey.OP_CONNECT);          //  0
            System.out.println(~key2.interestOps() & SelectionKey.OP_READ);             //  0
            System.out.println(~key2.interestOps() & SelectionKey.OP_WRITE);            //  4
            System.out.println("");

            System.out.println(~key3.interestOps() & SelectionKey.OP_ACCEPT);           // 16
            System.out.println(~key3.interestOps() & SelectionKey.OP_CONNECT);          // 0
            System.out.println(~key3.interestOps() & SelectionKey.OP_READ);             // 0
            System.out.println(~key3.interestOps() & SelectionKey.OP_WRITE);            // 0
            System.out.println("");

            // 重新定义感兴趣的事件
            key3.interestOps(SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE);
            System.out.println(~key3.interestOps() & SelectionKey.OP_ACCEPT);           // 16
            System.out.println(~key3.interestOps() & SelectionKey.OP_CONNECT);          // 0
            System.out.println(~key3.interestOps() & SelectionKey.OP_READ);             // 1
            System.out.println(~key3.interestOps() & SelectionKey.OP_WRITE);            // 0

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
