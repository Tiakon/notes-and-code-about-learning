package cn.tiakon.learn.java.io.bio.groupchat.common;

import java.io.*;
import java.net.Socket;

/**
 * @author Administrator
 */
public class CommonUtils {

    public static BufferedReader getBufferedReaderBySocket(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static BufferedWriter getBufferedWriterBySocket(Socket socket) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public static BufferedReader getBufferedReaderBySystemIn() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

}
