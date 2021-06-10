package cn.tiakon.webserver.connector;

import cn.tiakon.webserver.processor.ServletProcessor;
import cn.tiakon.webserver.processor.StaticProcessor;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable {

    private static final int DEFAULT_PORT = 8080;

    private ServerSocket serverSocket;
    private int port;

    public Connector() {
        this.port = DEFAULT_PORT;
    }

    public Connector(int port) {
        this.port = port;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println(">> 服务启动,监听端口: ".concat("[ " + port + " ] ").concat("...."));

            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Request request = new Request(inputStream);
            request.parse();

            Response response = new Response(outputStream);
            response.setRequest(request);

            if (request.getRequestUri().startsWith("/servlet/")) {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(request, response);
            }else{
                StaticProcessor staticProcessor = new StaticProcessor();
                staticProcessor.process(request, response);
            }
            close(socket);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(serverSocket);
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
