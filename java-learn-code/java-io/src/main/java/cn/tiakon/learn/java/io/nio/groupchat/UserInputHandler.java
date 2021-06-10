package cn.tiakon.learn.java.io.nio.groupchat;

import cn.tiakon.learn.java.io.bio.groupchat.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Administrator
 */
@Slf4j
public class UserInputHandler implements Runnable {

    private ChatClient client;

    public UserInputHandler(ChatClient client) throws IOException {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader consoleReader = CommonUtils.getBufferedReaderBySystemIn();
            while (true) {
                String input = consoleReader.readLine();
                // 向服务器端发送消息
                client.send(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(">> 停止输入，准备退出...");
        }
    }
}
