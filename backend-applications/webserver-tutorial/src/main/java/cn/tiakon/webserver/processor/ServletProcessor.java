package cn.tiakon.webserver.processor;

import cn.tiakon.webserver.connector.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Administrator
 */
public class ServletProcessor {

    URLClassLoader getServletLoader() throws MalformedURLException {
        File webRootFile = new File(ConnectorUtils.WEB_ROOT);
        URL webRootUrl = webRootFile.toURI().toURL();
        return new URLClassLoader(new URL[]{webRootUrl});
    }

    Servlet getServlet(URLClassLoader loader, Request request) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String uri = request.getRequestUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(servletName);
        Class servletClass = loader.loadClass(servletName);
        System.out.println(servletClass.getSimpleName());
        System.out.println(servletClass.getName());
        System.out.println(servletClass.getCanonicalName());
        Servlet servlet = (Servlet) servletClass.newInstance();
        return servlet;
    }

    public void process(Request request, Response response) throws IOException {
        URLClassLoader loader = getServletLoader();
        try {
            Servlet servlet = getServlet(loader, request);
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet.service(requestFacade, responseFacade);
        } catch (ClassNotFoundException | IllegalAccessException | ServletException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
