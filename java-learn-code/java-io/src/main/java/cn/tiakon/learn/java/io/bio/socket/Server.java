package cn.tiakon.learn.java.io.bio.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Administrator
 */
@Slf4j
public class Server {

    public static void main(String[] args) {
        final String QUIT = "quit";
        final int DEFAULT_SERVER_PORT = 9999;
        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(DEFAULT_SERVER_PORT);
            log.info(">> 启动服务器...");
            log.info(">> 开始监听 [{}]", DEFAULT_SERVER_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                log.info(">> 客户端[{}]已连接...", socket.getPort());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line = null;
                // 接收客户端消息
                while ((line = reader.readLine()) != null) {
                    log.info(">> 客户端 [{}] 接收 ：{}", socket.getPort(), line);
                    writer.write(line + "\r\n");
                    writer.flush();
                    if (QUIT.equals(line)) {
                        log.info(">> 客户端 [{}] 已断开连接 ", socket.getPort());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                log.info(">> 服务器[关闭].");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
