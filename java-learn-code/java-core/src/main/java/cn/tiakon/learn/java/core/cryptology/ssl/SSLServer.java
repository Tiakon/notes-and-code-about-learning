package cn.tiakon.learn.java.core.cryptology.ssl;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 *
 * SSL分为单向认证和双向认证。单向认证是客户端信任服务端，双向认证是客户端既要信任服务端而且服务端也要信任客户端。
 *
 * 不管是客户端还是服务端，都要存以下两样keystore
 *
 * KeyStore：保存自己的公钥和私钥。
 * Trust KeyStore：保存对方的公钥证书。
 *
 * 如果是单向认证，服务器端的KeyStore存储自己的公钥和私钥，客户端的Trust KeyStore要导入服务器端的公钥证书。
 * 如果是双向认证，在上面的基础上，客户端的KeyStore存储自己的公钥和私钥，服务器端的Trust KeyStore要导入客户端的公钥证书。
 *
 * 以上4个KeyStore可以通过以下命令生成：
 *
 * keytool -genkey -alias serverkey -keystore server.keystore
 * keytool -export -alias serverkey -keystore server.keystore -file server.crt
 * keytool -import -alias serverkey -file server.crt -keystore tclient.keystore
 *
 * keytool -genkey -alias clientkey -keystore client.keystore
 * keytool -export -alias clientkey -keystore client.keystore -file client.crt
 * keytool -import -alias clientkey -file client.crt -keystore tserver.keystore
 *
 * 最后看下代码实现，主要通过SSLContext对象的init方法载入KeyStore
 *
 * */
public class SSLServer {
    private SSLServerSocket serverSocket;
    private int PORT = 8443;
    private String workPath = "conf".concat(File.separator);
    private String serverKeyStorePath = workPath.concat("server.keystore");
    private String SERVER_KEY_STORE_PASSWORD = "123456";
    private String serverTrustKeyStorePath = workPath+"tserver.keystore";
    private String SERVER_TRUST_KEY_STORE_PASSWORD = "123456";

    public SSLServer() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS"); // 创建JKS密钥库
            ks.load(new FileInputStream(serverKeyStorePath), SERVER_KEY_STORE_PASSWORD.toCharArray());
            // 创建管理JKS密钥库的X.509密钥管理器
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());

            KeyStore tks = KeyStore.getInstance("JKS");
            tks.load(new FileInputStream(serverTrustKeyStorePath), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(tks);

            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            // 根据上面配置的SSL上下文来产生SSLServerSocketFactory,与通常的产生方法不同
            SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
            serverSocket = (SSLServerSocket) factory.createServerSocket(PORT);
        } catch (UnrecoverableKeyException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        while(true){
            try {
                SSLSocket socket = (SSLSocket)serverSocket.accept();
                InputStream in =socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                byte[] b = new byte[1024];
                in.read(b);
                System.out.println(new String(b));

                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SSLServer sslServer = new SSLServer();
        sslServer.start();
    }
}


