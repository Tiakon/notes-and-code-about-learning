package cn.tiakon.learn.java.concurrency.imooc.jimin;

import cn.tiakon.learn.java.concurrency.imooc.jimin.threadlocal.RequestHandler;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class HttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

//        String user = httpServletRequest.getSession().getAttribute("user").toString();

        log.info("do filter {}:{}", Thread.currentThread().getId(), ((HttpServletRequest) servletRequest).getServletPath());
        RequestHandler.add(Thread.currentThread().getId());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
