package cn.tiakon.webserver.test.until;

import cn.tiakon.webserver.connector.ConnectorUtils;
import cn.tiakon.webserver.connector.Request;
import cn.tiakon.webserver.connector.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebServerUtilTest {

    public static Request getRequest(String requestHead) {
        InputStream inputStream = new ByteArrayInputStream(requestHead.getBytes());
        Request request = new Request(inputStream);
        request.parse();
        return request;
    }

    public static Response getResponse(Request request, ByteArrayOutputStream byteArrayOutputStream) {
        Response response = new Response(byteArrayOutputStream);
        response.setRequest(request);
        return response;
    }

    public static String readHtmlFileContent(Request request) throws IOException {
        try {
            return readFileToStrByFileStream(new File(ConnectorUtils.WEB_ROOT, request.getRequestUri()));
        } catch (Exception e) {
            return readFileToStrByFileStream(new File(ConnectorUtils.WEB_ROOT, "404.html"));
        }
    }

    public static String readFileToStrByFileStream(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int length = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((length = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        fileInputStream.close();
        return outputStream.toString();
    }

    public static String readHtmlFileContentPlus(Request request) throws IOException {
        try {
            return readFileToStrByFileUtils(ConnectorUtils.WEB_ROOT.concat(File.separator).concat(request.getRequestUri()));
        } catch (Exception e) {
            return readFileToStrByFileUtils(ConnectorUtils.WEB_ROOT.concat(File.separator).concat("404.html"));
        }
    }

    public static String readFileToStrByFileUtils(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}
