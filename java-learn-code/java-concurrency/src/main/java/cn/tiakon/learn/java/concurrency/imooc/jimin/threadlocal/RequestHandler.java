package cn.tiakon.learn.java.concurrency.imooc.jimin.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHandler {

    private final static ThreadLocal<Long> requestHandler = new ThreadLocal<>();

    public static void add(Long id) {
        requestHandler.set(id);
    }

    public static Long getId() {
        return requestHandler.get();
    }

    public static void remove() {
        requestHandler.remove();
    }
}
