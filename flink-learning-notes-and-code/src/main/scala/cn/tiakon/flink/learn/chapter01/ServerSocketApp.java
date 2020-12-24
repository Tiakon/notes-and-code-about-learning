package cn.tiakon.flink.learn.chapter01;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
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
                String line = scanner.nextLine();
                bufferedOutputStream.write(line.getBytes(), 0, line.length());
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
