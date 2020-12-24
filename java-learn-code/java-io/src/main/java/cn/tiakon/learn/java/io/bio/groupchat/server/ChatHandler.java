package cn.tiakon.learn.java.io.bio.groupchat.server;

import cn.tiakon.learn.java.io.bio.groupchat.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Administrator
 */
@Slf4j
public class ChatHandler implements Runnable {

    private String quitStr;

    private ChatServer chatServer;
    private Socket currentSocket;

    public ChatHandler(ChatServer chatServer, Socket socket) {
        this.currentSocket = socket;
        this.chatServer = chatServer;
        quitStr = ">> 客户端[" + socket.getPort() + "]:quit";
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = CommonUtils.getBufferedReaderBySocket(currentSocket);
            String line;
            // 接收客户端消息
            while ((line = reader.readLine()) != null) {
                if (quitStr.equals(line)) {
                    line = ">> 客户端[" + currentSocket.getPort() + "]:断开连接...";
                    chatServer.sendMsgAllClientSocket(line);
                    break;
                } else {
                    chatServer.sendMsgAllClientSocket(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatServer.removeClientSocket(currentSocket);
            log.info(">> 客户端[{}]:断开连接...", currentSocket.getPort());
        }
    }
}
