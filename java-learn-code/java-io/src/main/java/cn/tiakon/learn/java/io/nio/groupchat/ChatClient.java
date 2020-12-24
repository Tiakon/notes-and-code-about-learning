package cn.tiakon.learn.java.io.nio.groupchat;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

@Slf4j
public class ChatClient {

    private static final String DEFAULT_SERVER_HOST = "127.0.0.1";
    private static final int DEFAULT_SERVER_PORT = 7777;
    private static final String QUIT = "quit";
    private static final int BUFFER_SIZE = 1024 * 8;

    private String host;
    private int port;

    private Selector selector;
    private SocketChannel clientSocketChannel;
    private Charset charset = Charset.forName("UTF-8");
    private ByteBuffer readByteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private ByteBuffer writeByteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public ChatClient() {
        this(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
    }

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void start() {

        try {
            clientSocketChannel = SocketChannel.open();
            clientSocketChannel.configureBlocking(false);

            selector = Selector.open();

            log.debug(">> clientSocketChannel 执行 register()...");
            clientSocketChannel.register(selector, SelectionKey.OP_CONNECT);
            clientSocketChannel.connect(new InetSocketAddress(host, port));

            while (true) {
                log.debug(">> 执行 select()...");
                selector.select();

                log.debug(">> 开始获取 selectionKeys...");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                log.debug(">> 开始遍历 selectionKeys...");
                for (SelectionKey selectionKey : selectionKeys) {
                    log.debug(">> 开始事件处理...");
                    handles(selectionKey);
                }

                log.debug(">> 执行 clear()...");
                selectionKeys.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(selector);
        }


    }

    private void handles(SelectionKey selectionKey) throws IOException {
        // CONNECT 事件 - 连接就绪事件
        if (selectionKey.isConnectable()) {
            log.debug(">> 连接事件被触发...");
            SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
            if (clientSocketChannel.isConnectionPending()) {
                clientSocketChannel.finishConnect();
                log.info(">> 连接完成。");
                log.info(">> 等待用户输入...");
                // 处理用户的输入
                new Thread(new UserInputHandler(this)).start();
            }
            log.debug(">> clientSocketChannel 执行 register()...");
            clientSocketChannel.register(selector, SelectionKey.OP_READ);
        }
        // READ 事件 - 服务器转发消息
        else if (selectionKey.isReadable()) {
            log.debug(">> 可读事件被触发...");
            SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
            String msg = receive(clientSocketChannel);
            if (msg.isEmpty()) {
                // 服务器异常
                close(selector);
            } else {
                System.out.println(msg);
            }


        }

    }

    private String receive(SocketChannel clientSocketChannel) throws IOException {
        readByteBuffer.clear();
        while (clientSocketChannel.read(readByteBuffer) > 0) ;
        readByteBuffer.flip();
        return String.valueOf(charset.decode(readByteBuffer));
    }

    public void send(String msg) throws IOException {
        if (msg.isEmpty()) {
            return;
        }

        writeByteBuffer.clear();
        writeByteBuffer.put(charset.encode(msg));
        writeByteBuffer.flip();
        while (writeByteBuffer.hasRemaining()) {
            clientSocketChannel.write(writeByteBuffer);
        }
        // 检查用户是否准备退出
        if (readyToQuit(msg)) {
            System.out.println(getCilentSocketPort(clientSocketChannel) + ",已断开...");
            close(selector);
            System.exit(0);
        }

    }

    private int getCilentSocketPort(SocketChannel clientSocketChannel) {
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
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }


}
