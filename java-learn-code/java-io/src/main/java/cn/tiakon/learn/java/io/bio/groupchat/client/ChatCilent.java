package cn.tiakon.learn.java.io.bio.groupchat.client;

import cn.tiakon.learn.java.io.bio.groupchat.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 */
@Slf4j
public class ChatCilent {

    private String serverIp;
    private int serverPort;

    public ChatCilent(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    private void start() {

        Socket socket = null;
        try {
            log.info(">> 客户端启动...");
            log.info(">> 连接服务器: {}:{}", serverIp, serverPort);

            socket = new Socket(serverIp, serverPort);

            log.info(">> 连接成功！");
            BufferedReader reader = CommonUtils.getBufferedReaderBySocket(socket);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new ClientInputHandler(socket));
            String quitStr = ">> 客户端[" + socket.getLocalPort() + "]:断开连接...";

            String input;
            // 从服务器段读消息
            while ((input = reader.readLine()) != null) {
                // 打印服务器消息
                System.out.println(input);
                if (quitStr.equals(input)) {
                    break;
                }
            }
            executorService.shutdown();
            System.out.println(">> 客户端关闭。");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ChatCilent chatCilent = new ChatCilent("127.0.0.1", 9999);
        chatCilent.start();
    }

}
