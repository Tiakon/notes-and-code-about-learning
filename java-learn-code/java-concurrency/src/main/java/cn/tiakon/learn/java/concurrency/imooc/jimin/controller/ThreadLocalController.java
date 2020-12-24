package cn.tiakon.learn.java.concurrency.imooc.jimin.controller;

import cn.tiakon.learn.java.concurrency.imooc.jimin.threadlocal.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Administrator
 */
@Controller
@Slf4j
@RequestMapping("/threadLocal")
public class ThreadLocalController {

    private long count = 0;

    @RequestMapping("/test")
    @ResponseBody
    public Long test() {
        log.info("第 {} 次请求...", ++count);
        return RequestHandler.getId();
    }
}
