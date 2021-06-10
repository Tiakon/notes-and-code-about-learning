package cn.tiakon.app.java.lp.example;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

/**
 * JUL Api 的使用案例
 * last time   : 2020/9/15 14:00
 *
 * @author tiankai.me@gmail.com on 2020/9/15 14:00.
 */
public class JulDemoApiTest {

    /**
     * 日志记录器多值打印
     * last time   : 2020/9/17 10:41
     *
     * @author tiankai.me@gmail.com on 2020/9/17 10:41.
     */
    @Test
    public void quickTest() {
        Logger logger = Logger.getLogger("cn.tiakon.app.java.lp.example.JulDemoApiTest");
        logger.info(">> hello world!!!");
        logger.log(Level.INFO, ">> hello world!!!");
        String name = "张三";
        int age = 19;
        logger.log(Level.INFO, ">> {0} {1}", new Object[]{name, age});
    }

    /**
     * 日志记录器所有层级
     * last time   : 2020/9/17 10:41
     *
     * @author tiankai.me@gmail.com on 2020/9/17 10:41.
     */
    @Test
    public void logLevelTest() {
        // 1. 获取日志记录起对象
        Logger logger = Logger.getLogger("cn.tiakon.app.java.lp.example.JulDemoApiTest");

        // 2. 日志记录输出
        logger.severe("severe");
        logger.warning("warning");
        // 默认日志输出级别
        logger.info("info");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    /**
     * 日志记录器层级设置与输出方式
     * last time   : 2020/9/17 10:40
     *
     * @author tiankai.me@gmail.com on 2020/9/17 10:40.
     */
    @Test
    public void logLevelConfigTest() throws IOException {
        // 1. 获取日志记录起对象
        Logger logger = Logger.getLogger("cn.tiakon.app.java.lp.example.JulDemoApiTest");

        // 关闭系统默认配置
        logger.setUseParentHandlers(false);

        // 创建简单格式转换对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();

        // 创建控制台输出
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler("logs/jul.log");
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);

        // 自定义配置日志级别
        // 配置日志具体级别
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);

        // 2. 日志记录输出
        logger.severe("severe");
        logger.warning("warning");
        // 默认日志输出级别
        logger.info("info");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    /**
     * 日志记录器父子关系
     * last time   : 2020/9/17 10:40
     *
     * @author tiankai.me@gmail.com on 2020/9/17 10:40.
     */
    @Test
    public void logParentTest() {

        Logger logger1 = Logger.getLogger("cn.tiakon");
        Logger logger2 = Logger.getLogger("cn");

        System.out.println(logger1.getParent() == logger2);

        // 所有日志记录器的顶级父类 LogManager$RootLogger ,名称·为空字符串
        System.out.println("logger2 Parent: ".concat(logger2.getParent().toString()).concat(" name: ").concat(logger2.getParent().getName()));

        // 设置logger2 日志级别

        // 关闭系统默认配置
        logger2.setUseParentHandlers(false);

        // 创建控制台输出
        ConsoleHandler consoleHandler = new ConsoleHandler();

        // 创建简单格式转换对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        consoleHandler.setFormatter(simpleFormatter);

        logger2.addHandler(consoleHandler);

        // 设置日志级别
        logger2.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);

        // 2. 日志记录输出
        logger2.severe("severe");
        logger2.warning("warning");
        // 默认日志输出级别
        logger2.info("info");
        logger2.fine("fine");
        logger2.finer("finer");
        logger2.finest("finest");

    }


    /**
     * 加载自定义配置
     * last time   : 2020/9/17 11:00
     *
     * @author tiankai.me@gmail.com on 2020/9/17 11:00.
     */
    @Test
    public void logPropertiesTest() {
        try {

            InputStream resourceAsStream = JulDemoApiTest.class.getClassLoader().getResourceAsStream("logging.properties");
            LogManager logManager = LogManager.getLogManager();
            logManager.readConfiguration(resourceAsStream);

            Logger logger = Logger.getLogger("cn.tiakon.app.java.lp.example");

            logger.severe("severe");
            logger.warning("warning");
            logger.info("info");
            logger.config("config");

            logger.fine("fine");
            logger.finer("finer");
            logger.finest("finest");

            Logger logger2 = Logger.getLogger("JulDemoApiTest");

            logger2.severe("severe all");
            logger2.warning("warning all");
            logger2.info("info all");
            logger2.config("config all");
            logger2.fine("fine all");
            logger2.finer("finer all");
            logger2.finest("finest all");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
