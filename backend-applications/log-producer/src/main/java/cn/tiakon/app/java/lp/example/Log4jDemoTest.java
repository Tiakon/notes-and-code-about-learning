package cn.tiakon.app.java.lp.example;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.junit.Test;

/**
 * Log4j Api 的使用案例
 * last time   : 2020/9/17 13:34
 *
 * @author tiankai.me@gmail.com on 2020/9/17 13:34.
 */
public class Log4jDemoTest {

    @Test
    public void quickTest() throws InterruptedException {

        // 开启 log4j 内置日志记录
        LogLog.setInternalDebugging(true);

        // 0. 初始化配置信息，也可用代码实现
//        BasicConfigurator.configure();

        // 1. 获取日志对象
        Logger logger = Logger.getLogger(Log4jDemoTest.class);

        // 2. 日志记录输出
        logger.fatal("严重错误，一般会造成系统崩溃并终止运行");
        logger.error("错误信息，不会影响系统运行");
        logger.warn("警告信息，可能会发生问题");
        logger.info("运行信息，数据库连接、网络连接、IO操作");
        logger.debug("调式信息，一般在开发中使用。记录程序变量参数传递信息等");
        logger.trace("追踪信息，记录程序所有的流程信息");

       /* for (int i = 0; i < 1000; i++) {
            logger.fatal("严重错误，一般会造成系统崩溃并终止运行");
            logger.error("错误信息，不会影响系统运行");
            logger.warn("警告信息，可能会发生问题");
            logger.info("运行信息，数据库连接、网络连接、IO操作");
            logger.debug("调式信息，一般在开发中使用。记录程序变量参数传递信息等");
            logger.trace("追踪信息，记录程序所有的流程信息");
//            Thread.sleep(100);
        }*/

        // 1. 获取日志对象
        Logger logger2 = Logger.getLogger(Logger.class);

        // 2. 日志记录输出
        logger2.fatal("严重错误，一般会造成系统崩溃并终止运行");
        logger2.error("错误信息，不会影响系统运行");
        logger2.warn("警告信息，可能会发生问题");
        logger2.info("运行信息，数据库连接、网络连接、IO操作");
        logger2.debug("调式信息，一般在开发中使用。记录程序变量参数传递信息等");
        logger2.trace("追踪信息，记录程序所有的流程信息");

    }


}
