package cn.tiakon.webserver.test;

import cn.tiakon.webserver.connector.Request;
import cn.tiakon.webserver.connector.Response;
import cn.tiakon.webserver.test.until.WebServerUtilTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WebServerTest {

    final String validRequest = "GET /index.html HTTP/1.1";
    final String invalidResponse = "GET /notfound.html HTTP/1.1";

    final String static200 = "HTTP/1.1 200 OK\r\n\r\n";
    final String static404 = "HTTP/1.1 404 File Not Found\r\n\r\n";

//    final String validResponse = "GET /index.html HTTP/1.1";


    @Test
    public void getRequestUriTest() {
        Request request = WebServerUtilTest.getRequest(validRequest);
        Assert.assertEquals("/index.html", request.getRequestUri());
    }

    @Test
    public void Response200Test() {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            // 根据url中的请求，解析webroot下的资源。
            Request request = WebServerUtilTest.getRequest(validRequest);
            Response response = WebServerUtilTest.getResponse(request, byteArrayOutputStream);
            response.sendStaticResource();

//            String resource = WebServerUtilTest.readHtmlFileContent(request);
            String resource = WebServerUtilTest.readHtmlFileContentPlus(request);
            Assert.assertEquals(static200.concat(resource), byteArrayOutputStream.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void Response404Test() {

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            Request request = WebServerUtilTest.getRequest(invalidResponse);
            Response response = WebServerUtilTest.getResponse(request, byteArrayOutputStream);
            response.sendStaticResource();

//            String resource = WebServerUtilTest.readHtmlFileContent(request);
            String resource = WebServerUtilTest.readHtmlFileContentPlus(request);

            Assert.assertEquals(static404.concat(resource), byteArrayOutputStream.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
