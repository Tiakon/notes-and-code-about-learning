package cn.tiakon.app.java.lp.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常见的日志门面 ：
 * JCL、slf4j
 * 常见的日志实现：
 * JUL、log4j、logback、log4j2
 * <p>
 * 日志框架出现的历史顺序：
 * log4j -->JUL-->JCL--> slf4j --> logback --> log4j2
 * <p>
 * last time   : 2020/9/17 17:38
 *
 * @author tiankai.me@gmail.com on 2020/9/17 17:38.
 */
public class Slf4jDemoTest {

    Logger logger = LoggerFactory.getLogger(Slf4jDemoTest.class);

    @Test
    public void quickTest() {

        // 日志级别
        logger.error("error");
        logger.warn("warn");
        logger.info("info");
        logger.debug("debug");
        logger.trace("trace");

        // 使用占位符输出日志信息

        String name = "tk";
        int age = 26;
        logger.info("用户:{}，年龄:{}", name, age);

        // 将系统异常打印
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("出现异常", e);
        }
    }

 }
