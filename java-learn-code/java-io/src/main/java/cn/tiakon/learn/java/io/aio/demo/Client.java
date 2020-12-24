package cn.tiakon.learn.java.io.aio.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class Client {
    final String LOCALHOST = "localhost";
    final int DEFAULT_PORT = 8888;

    AsynchronousSocketChannel clientSocketChannel;

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                log.info(">> {} 已关闭...", DEFAULT_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(clientSocketChannel);
            }
        }
    }

    public void start() {
        try {
            // 创建 channel
            clientSocketChannel = AsynchronousSocketChannel.open();
            Future<Void> connectFuture = clientSocketChannel.connect(new InetSocketAddress(LOCALHOST, DEFAULT_PORT));
            connectFuture.get();

            // 等待用户的输入
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String readLine = consoleReader.readLine();
                byte[] readLineBytes = readLine.getBytes();
                ByteBuffer byteBuffer = ByteBuffer.wrap(readLineBytes);
                Future<Integer> writeResult = clientSocketChannel.write(byteBuffer);
                writeResult.get();
                byteBuffer.flip();
                Future<Integer> readResult = clientSocketChannel.read(byteBuffer);
                readResult.get();
                String echo = new String(byteBuffer.array());
                System.out.println(echo);
                byteBuffer.clear();
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            close(clientSocketChannel);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
