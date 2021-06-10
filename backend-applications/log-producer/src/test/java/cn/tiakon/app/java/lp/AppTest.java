//package cn.tiakon.app.java.lp;
//
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Properties;
//import java.util.regex.Pattern;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
///**
// * Unit test for simple App.
// */
//public class AppTest {
//
//    private final static Logger logger = LoggerFactory.getLogger(AppTest.class);
//
//    @Test
//    public void shouldAnswerWithTrue() {
//        assertTrue(true);
//    }
//
//    @Test
//    public void proPertiesTest() throws IOException {
//        String filePath = "conf" + File.separator + "producer-conf.properties";
//        File file = new File("");
//
////        System.out.println(file.getParent());
////        System.out.println(file.getPath());
//
//        String absolutePath = file.getAbsolutePath();
////        System.out.println(absolutePath);
//
//        String canonicalPath = file.getCanonicalPath();
////        System.out.println(canonicalPath);
//
////        System.out.println(canonicalPath + File.separator + filePath);
//
//        Properties props = new Properties();
//        props.load(new FileInputStream(canonicalPath + File.separator + filePath));
//        String logType = props.getProperty("logType", "iqiyi");
//        int recodeNum = Integer.valueOf(props.getProperty("recodeNum", "10"));
//
//        assertEquals("iqiyi", logType);
//        assertEquals(5, recodeNum);
//
//
//    }
//
//    //Wins或Linux下匹配路径的正则表达式
//    @Test
//    public void WinAndLinuxPathPatternTest() {
//        String filePath = "E:\\IdeaProjects\\log-producer\\conf\\producer-conf.properties";
//        String filePath2 = "/home/kai.tian/log-producer/conf/producer-conf.properties";
//        String filePath3 = "\\a\\6\\.";
//        String filePath4 = "E:\\";
//        String patternStr = "(^/[a-zA-Z0-9-_./]*$)|(^[CEDF][:][\\\\a-zA-Z0-9-_.]*$)";
//        String patternStr2 = "\\\\a\\\\6\\\\\\.";
//        String patternStr3 = "(^[CDFE][:](\\\\)$)";
//        assertTrue(Pattern.matches(patternStr, filePath));
//    }
//
//    @Test
//    public void startCalcuTest() {
//        String message = "1569764868.000\u00020\u0002120.52.73.133\u0002193.178.190.161\u0002-/404\u0002320\u0002GET\u0002http://testbshls.live.migucloud.com\u0002-\u0002NONE/-\u0002-\u0002\"RefererString\"\u0002\"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0\"\u0002param1=CookieString\u0002-\u0002B\u0002-\u0002-\u0002-\u0002-\u0002\"-\"\u0002\"HTTP/1.0\"\u000235855\u0002-\u000280\u0002-\u0002-\u0002http\u0002410\u0002-\u0002-\u0002-\u0002-\u0002normal\u0002xlwl.schdxww.com\u000245.120.103.123\u0002\"[29/Sep/2019:20:59:57 +0800]\"\u0002320\u000227727\u000231220\u000215610\u0002-\u0002-\u0002162\u0002\"OK\"\u0002-\u00020\u0002-";
//        String accessLogPath = "E:\\IdeaProjects\\log-producer\\logs\\access-out.log";
//        int totalNum = 100;
//        LogProducerStarter.startCalculation(message, accessLogPath, totalNum);
//
//        /*String[] messageArr = message.split("\\002", 2);
//
//        System.out.println(messageArr[0]);
//        System.out.println(messageArr[1]);
//
//        long currentTimeMillis = System.currentTimeMillis();
//        String currentTimeMillisStr = String.valueOf(currentTimeMillis);
//        String millis = currentTimeMillisStr.substring(currentTimeMillisStr.length() - 3, currentTimeMillisStr.length());
//        String timestampFormatStr = currentTimeMillis / 1000 + "." + millis;
//        System.out.println(currentTimeMillis);
//        System.out.println(timestampFormatStr);
//        System.out.println(timestampFormatStr+"\002"+messageArr[1]);*/
//
//    }
//
//    @Test
//    public void getLogTest() {
//        logger.info(LogProducerStarter.getStarterLogStr());
//    }
//
//
//}
