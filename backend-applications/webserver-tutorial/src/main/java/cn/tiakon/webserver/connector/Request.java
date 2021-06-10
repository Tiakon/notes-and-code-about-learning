package cn.tiakon.webserver.connector;


import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 根据发送的http请求解析URL。
 *
 * @author Administrator
 */
public class Request implements ServletRequest {

// 请求头
//         GET /index.html HTTP/1.1
//         Host: localhost:8888
//         Connection: keep-alive
//         Pragma: no-cache
//         Cache-Control: no-cache
//         Upgrade-Insecure-Requests: 1
//         User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36
//         Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9

    private static final int BUFFER_SIZE = 1024;

    private InputStream input;

    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    public String getRequestUri() {
        return uri;
    }

    public void parse() {
        int length = 0;
        byte[] bufferBytes = new byte[BUFFER_SIZE];
        try {
            length = input.read(bufferBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder requestBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            requestBuilder.append((char) bufferBytes[i]);
        }

        uri = parseUri(requestBuilder.toString());
    }
    /**
     * 解析http请求，获取 uri
     * last time   : 2020/7/30 21:26
     *
     * @author tiankai.me@gmail.com on 2020/7/30 21:26.
     */
    private String parseUri(String requestStr) {
        int index1, index2;
        if ((index1 = requestStr.indexOf(" ")) != -1) {
            if ((index2 = requestStr.indexOf(" ", index1 + 1)) != -1) {
                if (index2 > index1) {
                    return requestStr.substring(index1 + 1, index2);
                }
            }
        }
        return "";
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }
}
