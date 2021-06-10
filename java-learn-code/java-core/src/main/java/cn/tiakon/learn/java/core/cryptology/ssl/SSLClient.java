package cn.tiakon.learn.java.core.cryptology.ssl;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class SSLClient {
    private SSLSocket socket;
    private int PORT = 8443;
    private String workPath = "conf".concat(File.separator);
    private String clientKeyStorePath = workPath + "client.keystore";
    private String CLIENT_KEY_STORE_PASSWORD = "123456";
    private String clientTrustKeyStorePath = workPath + "tclient.keystore";
    private String CLIENT_TRUST_KEY_STORE_PASSWORD = "123456";

    public SSLClient() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS"); // 创建JKS密钥库
            ks.load(new FileInputStream(clientKeyStorePath), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            // 创建管理JKS密钥库的X.509密钥管理器
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());

            KeyStore tks = KeyStore.getInstance("JKS");
            tks.load(new FileInputStream(clientTrustKeyStorePath), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(tks);

            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            // 根据上面配置的SSL上下文来产生SSLServerSocketFactory,与通常的产生方法不同
            SSLSocketFactory factory = sslContext.getSocketFactory();
            socket = (SSLSocket) factory.createSocket("127.0.0.1", PORT);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | UnrecoverableKeyException | IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            out.write("Hello World !!!".getBytes());

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SSLClient sslClient = new SSLClient();
        sslClient.start();
    }
}
