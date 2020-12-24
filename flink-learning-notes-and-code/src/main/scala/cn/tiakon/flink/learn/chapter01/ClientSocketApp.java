package cn.tiakon.flink.learn.chapter01;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ClientSocketApp {
    public static void main(String[] args) {


        Socket socket = null;
        try {
            socket = new Socket("localhost", 7202);
            OutputStream outputStream = socket.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.next();
                bufferedOutputStream.write(line.getBytes(Charset.forName("utf-8")), 0, line.length());
                bufferedOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
