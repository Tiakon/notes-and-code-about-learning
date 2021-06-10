package cn.tiakon.webserver.processor;

import cn.tiakon.webserver.connector.Request;
import cn.tiakon.webserver.connector.Response;

import java.io.IOException;

public class StaticProcessor {

    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
