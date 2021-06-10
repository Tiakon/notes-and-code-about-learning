package cn.tiakon.webserver.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTest {

    public static void main(String[] args) throws IOException {

        System.out.println(System.getProperty("user.dir"));

        Socket socket = new Socket("localhost", 8080);
        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write("GET /index.html HTTP/1.1".getBytes());
//        outputStream.write("GET /notfound.html HTTP/1.1".getBytes());
        outputStream.write("GET /servlet/TimeServlet HTTP/1.1".getBytes());

        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();

        byte[] bytes = new byte[2048];
        int length = 0;
        StringBuilder builder = new StringBuilder();

        while ((length = inputStream.read(bytes)) != -1) {
            for (int i = 0; i < length; i++) {
                builder.append((char) bytes[i]);
            }
        }

        System.out.println(builder.toString());

        socket.shutdownInput();
        socket.close();
    }

}
