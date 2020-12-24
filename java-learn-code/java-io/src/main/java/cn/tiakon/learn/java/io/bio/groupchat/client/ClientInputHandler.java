package cn.tiakon.learn.java.io.bio.groupchat.client;

import cn.tiakon.learn.java.io.bio.groupchat.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Administrator
 */
@Slf4j
public class ClientInputHandler implements Runnable {

    private final String QUIT = "quit";
    private int socketLocalPort;

    private BufferedWriter writer;
    private BufferedReader consoleReader;

    public ClientInputHandler(Socket socket) throws IOException {
        this.socketLocalPort = socket.getLocalPort();
        this.writer = CommonUtils.getBufferedWriterBySocket(socket);
        this.consoleReader = CommonUtils.getBufferedReaderBySystemIn();
    }

    @Override
    public void run() {
        try {
            String input;
            while ((input = consoleReader.readLine()) != null) {
                // 向服务器端发送消息
                writer.write(">> 客户端[" + socketLocalPort + "]:" + input + "\r\n");
                writer.flush();
                if (QUIT.equals(input)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">> 停止输入，准备退出...");
        }
    }
}
