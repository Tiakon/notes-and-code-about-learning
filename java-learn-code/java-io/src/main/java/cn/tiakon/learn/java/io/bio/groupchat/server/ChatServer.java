package cn.tiakon.learn.java.io.bio.groupchat.server;

import cn.tiakon.learn.java.io.bio.groupchat.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Executors:
 * newSingleThreadExecutor:创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。
 * 此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
 * <p>
 * newCachedThreadPool:创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，
 * 此线程池又可以自动的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
 * <p>
 * newFixedThreadPool:创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，超出的线程会在队列中等待。
 * 如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
 * <p>
 * newScheduledThreadPool:创建一个周期线程池，支持定时及周期性任务执行。
 * <p>
 * 无论创建那种线程池 必须要调用ThreadPoolExecutor
 * <p>
 * 线程池类为 java.util.concurrent.ThreadPoolExecutor，常用构造方法为：
 * <p>
 * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue, RejectedExecutionHandler handler)
 * <p>
 * corePoolSize： 线程池维护线程的最少数量
 * maximumPoolSize：线程池维护线程的最大数量
 * keepAliveTime： 线程池维护线程所允许的空闲时间
 * unit： 线程池维护线程所允许的空闲时间的单位
 * workQueue： 线程池所使用的缓冲队列
 * handler： 线程池对拒绝任务的处理策略
 */

/**
 * @author Administrator
 */
@Slf4j
public class ChatServer {

    private int serverPort;
    private ExecutorService executorService;
    private Map<Integer, Socket> clientSocketMap;

    private ChatServer(int serverPort) {
        this.serverPort = serverPort;
        this.executorService = Executors.newFixedThreadPool(20);
        this.clientSocketMap = new HashMap<>();
    }

    public void addClientSocket(Socket socket) {
        clientSocketMap.put(socket.getPort(), socket);
    }

    public synchronized void removeClientSocket(Socket socket) {
        clientSocketMap.remove(socket.getPort());
    }

    public synchronized void sendMsgAllClientSocket(String message) throws IOException {
        Set<Map.Entry<Integer, Socket>> entrySet = clientSocketMap.entrySet();
        for (Map.Entry<Integer, Socket> socketEntry : entrySet) {
            BufferedWriter bufferedWriter = CommonUtils.getBufferedWriterBySocket(socketEntry.getValue());
            bufferedWriter.write(message + "\r\n");
            bufferedWriter.flush();
        }
    }

    private void printCurrentCilentSocket() {
        Set<Map.Entry<Integer, Socket>> entrySet = clientSocketMap.entrySet();
        for (Map.Entry<Integer, Socket> socketEntry : entrySet) {
            log.info(">> [{}],[{}]", socketEntry.getKey(), socketEntry.getValue().toString());
        }
    }

    private void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
            log.info(">> 服务器启动...");
            log.info(">> 开始监听本地端口 [{}]", serverPort);
            while (true) {
                // 侦听与此 Socket 建立的连接并接受。该方法将阻塞，直到建立连接为止。
                Socket socket = serverSocket.accept();
                log.info(">> 客户端[{}]已连接...", socket.getPort());
                this.addClientSocket(socket);
                executorService.submit(new ChatHandler(this, socket));
                this.printCurrentCilentSocket();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                log.info(">> 服务器[关闭].");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(9999);
        chatServer.start();
    }

}
