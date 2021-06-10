package cn.tiakon.learn.java.io.nio.groupchat;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * 1. 连接服务器端，退出标志识别。
 * 2. 客户端消息广播。
 * <p>
 * last time   : 2021/1/2 23:32
 *
 * @author tiankai.me@gmail.com on 2021/1/2 23:32.
 */
@Slf4j
public class ChatServer {

    private static final int DEFAULT_PORT = 7777;
    private static final int BUFFER_SIZE = 1024 * 8;
    private static final String QUIT = "quit";
    private int port;

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private Charset charset = StandardCharsets.UTF_8;
    private ByteBuffer readByteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private ByteBuffer writeByteBuffer = ByteBuffer.allocate(BUFFER_SIZE);


    public ChatServer() {
        this(DEFAULT_PORT);
    }

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {

        try {
            serverSocketChannel = ServerSocketChannel.open();
            // 关闭阻塞
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            // 监听所有channel的状态变化
            selector = Selector.open();
            // channel 向 Selector 注册监听状态。
            log.debug(">> serverSocketChannel 执行 register()...");
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info(">> 启动服务器，监听端口:{} ...", port);
            while (true) {
                // 扫描所有注册的 channel ，监听 channel 所触发的事件。
                log.debug(">> 执行 select()...");
                selector.select();
                log.debug(">> 开始获取 selectionKeys...");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                log.debug(">> 开始遍历 selectionKeys...");
                for (SelectionKey selectionKey : selectionKeys) {
                    log.debug(">> 开始事件处理...");
                    // 处理被触发的事件
                    handles(selectionKey);
                }
                log.debug(">> 执行 clear()...");
                // 完成处理后，清空过期事件。
                selectionKeys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 会先关闭所有已注册的 channel ，再关闭 Selector。
            close(selector);
        }
    }

    private void handles(SelectionKey selectionKey) throws IOException {
        // ACCEPT事件 - 和客户端建立连接
        if (selectionKey.isAcceptable()) {
            log.debug(">> 接受事件被触发...");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel clientSocketChannel = serverSocketChannel.accept();
            clientSocketChannel.configureBlocking(false);
            log.debug(">> clientSocketChannel 执行 register()...");
            clientSocketChannel.register(selector, SelectionKey.OP_READ);
            log.info(">> 客户端[{}],已连接...", getClientSocketPort(clientSocketChannel));
        }
        // READ事件   - 客户端发送了消息
        else if (selectionKey.isReadable()) {
            log.debug(">> 可读事件被触发...");
            SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
            String fwdMsg = receive(clientSocketChannel);
            if (fwdMsg.isEmpty()) {
                // 客户端异常,取消该channel的监听。
                log.debug(">> cancel() 被执行...");
                selectionKey.cancel();

                /**
                 * 使尚未返回的第一个选择操作立即返回。
                 *  如果在调用{@link #select（）}或{@link #select（long）}方法时当前阻止了另一个线程，则该调用将立即返回。
                 * 如果当前没有正在进行选择操作，则除非同时调用{@link #selectNow（）}方法，否则将立即返回这些方法之一的下一次调用。
                 * 在任何情况下，该调用返回的值都可以为非零。
                 * {@link #select（）}或{@link #select（long）}方法的后续调用将照常阻塞，除非在此期间再次调用此方法。
                 *  在两次连续选择之间多次调用此方法
                 *  操作与仅调用一次具有相同的效果。 </ p>
                 *
                 * */
                log.debug(">> wakeup() 被执行...");
                selector.wakeup();
            } else {
                forwardMessage(clientSocketChannel, fwdMsg);
                // 检查用户是否退出
                if (readyToQuit(fwdMsg)) {
                    selectionKey.cancel();
                    selector.wakeup();
                    log.info(">> 客户端[{}],已断开...", getClientSocketPort(clientSocketChannel));
                }
            }
        }
    }

    private void forwardMessage(SocketChannel clientSocketChannel, String fwdMsg) throws IOException {
        for (SelectionKey selectionKey : selector.keys()) {
            Channel connectedClient = selectionKey.channel();
            if (connectedClient instanceof ServerSocketChannel) {
                continue;
            }
            if (selectionKey.isValid() && (readyToQuit(fwdMsg) || !clientSocketChannel.equals(connectedClient))) {
                writeByteBuffer.clear();
                writeByteBuffer.put(charset.encode(getClientSocketPort(clientSocketChannel) + ":" + fwdMsg));
                writeByteBuffer.flip();
                while (writeByteBuffer.hasRemaining()) {
                    ((SocketChannel) connectedClient).write(writeByteBuffer);
                }
            }
        }
    }

    private String receive(SocketChannel clientSocketChannel) throws IOException {
        readByteBuffer.clear();
        while (clientSocketChannel.read(readByteBuffer) > 0) ;
        readByteBuffer.flip();
        return String.valueOf(charset.decode(readByteBuffer));
    }

    private int getClientSocketPort(SocketChannel clientSocketChannel) {
        return clientSocketChannel.socket().getPort();
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Boolean readyToQuit(String msg) {
        return QUIT.equals(msg);
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

}
