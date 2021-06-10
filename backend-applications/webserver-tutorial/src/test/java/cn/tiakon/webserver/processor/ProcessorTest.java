package cn.tiakon.webserver.processor;

import cn.tiakon.webserver.connector.Request;
import cn.tiakon.webserver.test.until.WebServerUtilTest;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.Servlet;
import java.net.MalformedURLException;
import java.net.URLClassLoader;

public class ProcessorTest {

    private static final String servletRequest = "GET /servlet/TimeServlet HTTP/1.1";

    @Test
    public void ServletProcessorTest() throws MalformedURLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Request request = WebServerUtilTest.getRequest(servletRequest);
        ServletProcessor servletProcessor = new ServletProcessor();
        URLClassLoader urlClassLoader = servletProcessor.getServletLoader();

        Servlet processorServlet = servletProcessor.getServlet(urlClassLoader, request);

        Assert.assertEquals("TimeServlet", processorServlet.getClass().getName());
    }

}
