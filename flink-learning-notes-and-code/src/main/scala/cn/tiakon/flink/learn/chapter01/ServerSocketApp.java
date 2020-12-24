package cn.tiakon.flink.learn.chapter01;

import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ServerSocketApp {
    public static void main(String[] args) {


        try {
            ServerSocket serverSocket = new ServerSocket(7202);
            Socket accept = serverSocket.accept();
            System.out.println(">> 连接建立...");
            System.out.println(">> 客户端端口:".concat(String.valueOf(accept.getPort())));
            OutputStream outputStream = accept.getOutputStream();
            InputStream inputStream = accept.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] bytes = new byte[1024];
            int readed = 0;
            while ((readed = bufferedInputStream.read(bytes, 0, bytes.length)) != -1) {
                System.out.println(Arrays.toString(bytes));
            }

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            bufferedOutputStream.write(line.getBytes(), 0, line.length());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
