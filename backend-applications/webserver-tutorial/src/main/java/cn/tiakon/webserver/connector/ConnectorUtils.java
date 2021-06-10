package cn.tiakon.webserver.connector;

import java.io.File;

public class ConnectorUtils {

    // R:\code\project-source\backend-applications\webserver-tutorial\webroot
    public static final String WEB_ROOT = System.getProperty("user.dir").concat(File.separator).concat("webroot");


    public static final String PROTOCOL = "HTTP/1.1";
    public static final String CARRIAGE = "\r";
    public static final String NEWLINE = "\n";
    public static final String SPACE = " ";

    public static String renderStatus(HttpStatus httpStatus) {
        System.out.println(WEB_ROOT);

        StringBuffer buffer = new StringBuffer(PROTOCOL)
                .append(SPACE)
                .append(httpStatus.getStatusCode())
                .append(SPACE).append(httpStatus.getDescribe())
                .append(CARRIAGE).append(NEWLINE)
                .append(CARRIAGE).append(NEWLINE);
        return buffer.toString();
    }

}
