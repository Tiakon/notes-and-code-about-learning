package cn.tiakon.app.java.lp.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 我们为什么要使用日志门面(JCL)：
 * 1. 面向接口开发，不再依赖具体的实现类。减少代码的耦合。
 * 2. 项目通过导入不同的日志实现类，可以灵活的切换日志框架。
 * 3. 统一API，方便开发者学习和使用。
 * 4. 统一配置便于项目日志的管理。
 * <p>
 * JCL Api 的使用案例。
 * last time   : 2020/9/17 17:02
 *
 * @author tiankai.me@gmail.com on 2020/9/17 17:02.
 */
public class JclDemoTest {

    /**
     * 没有添加 log4j 依赖时，使用 jdk 自带的 日志实现 jul。
     * 添加 log4j 依赖后，则使用 log4j 的日志实现。
     * <p>
     * last time   : 2020/9/17 17:04
     *
     * @author tiankai.me@gmail.com on 2020/9/17 17:04.
     */
    @Test
    public void quickTest() {
        Log logger = LogFactory.getLog(JclDemoTest.class);
        logger.info("hello world!!!");
    }

    /**
     * 使用 jdk 自带的 日志实现 jul
     * last time   : 2020/9/17 17:04
     *
     * @author tiankai.me@gmail.com on 2020/9/17 17:04.
     */
}
