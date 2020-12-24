package cn.tiakon.learn.java.io.bio.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class Cilent {

    public static void main(String[] args) {

        final String QUIT = "quit";
        final int DEFAULT_SERVER_PORT = 9999;
        final String DEFAULT_SERVER_IP = "127.0.0.1";
        Socket socket = null;
        BufferedWriter writer = null;
        try {
            log.info(">> 启动客户端...");
            log.info(">> 建立连接: {}:{}", DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT);

            socket = new Socket(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            log.info(">> 等待用户输入信息...");
            String input = null;
            while ((input = consoleReader.readLine()) != null) {

                writer.write(input + "\r\n");
                writer.flush();
                // 接收服务器消息
                String line = reader.readLine();
                log.info(">> 接收服务器：{}", line);
                if (QUIT.equals(input)) {
                    break;
                }
            }
            log.info(">> 客户端[准备断开连接]...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                    log.info(">> 客户端[关闭].");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
