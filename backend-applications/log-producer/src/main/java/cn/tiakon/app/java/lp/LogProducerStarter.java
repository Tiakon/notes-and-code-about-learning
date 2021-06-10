//package cn.tiakon.app.java.lp;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Properties;
//import java.util.regex.Pattern;
//
///**
// * cn.tiakon.logproducer.core.LogProducerStarter
// * last time   : 2019/10/14 19:36
// * last update : tiankai.me@gmail.com
// * status : test success
// *
// * @author Created by Tiakon on 2019/10/14 19:36.
// */
//public class LogProducerStarter {
//
//    private final static Logger logger = LoggerFactory.getLogger(LogProducerStarter.class);
//
//    public static void main(String[] args) {
//
//        logger.info(getStarterLogStr());
//
//        if (args.length != 1) {
//            logger.error(">>> Usage: ");
//            logger.error(">>> <properties>:配置文件名称;默认路径：use.dir+conf/producer-conf.properties");
//            System.exit(1);
//        }
////        String pattern = "\"(^/[a-zA-Z0-9-_./]*$)|(^[CEDF][:][\\\\a-zA-Z0-9-_.]*$)\"";
//        String pattern = "[a-zA-Z0-9-_]*\\.properties";
//        if (!Pattern.matches(pattern, args[0])) {
//            logger.error(">>> 文件名中只允许字母、数字、下划线、横杠。");
//            logger.error(">>> 参数1:配置文件名");
//            System.exit(1);
//        }
//        String propsName = args[0];
//        Properties props = new Properties();
//        try {
//            String filePath = "conf" + File.separator + propsName;
//            props.load(new FileInputStream(new File("").getCanonicalPath() + File.separator + filePath));
//        } catch (IOException e) {
//            logger.error(">>> 读取配置文件是出现错误:{}", e.getMessage());
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        String message = props.getProperty("logTemplate", "1569764868.000\u00020\u0002120.52.73.133\u0002193.178.190.161\u0002-/404\u0002320\u0002GET");
//        String accessLogPath = props.getProperty("accessLogPath", "logs/access.log");
//        int totalNum = Integer.valueOf(props.getProperty("totalNum", "10"));
//
//        logger.info(">> 开始计算...");
//        LogProducerStarter.startCalculation(message, accessLogPath, totalNum);
//        logger.info(">> 计算结束。");
//
//    }
//
//    public static void startCalculation(String message, String accessLogPath, int totalNum) {
//
//        try (FileWriter fileWriter = new FileWriter(accessLogPath, true);
//             PrintWriter printWriter = new PrintWriter(fileWriter)) {
//            long startTimestamp = System.currentTimeMillis();
//            // yyyyMMddHHmmSS
//            logger.info(">> start print time: {}", getDateByDiv(startTimestamp, "yyyy-MM-dd HH:mm:ss.SSS"));
//
//            //模拟实时产生的日志；1571052515274->1571052515.274
//            for (int i = 0; i < totalNum; i++) {
//                String[] messageArr = message.split("\\002", 2);
//                long currentTimeMillis = System.currentTimeMillis();
//                String currentTimeMillisStr = String.valueOf(currentTimeMillis);
//                String millis = currentTimeMillisStr.substring(currentTimeMillisStr.length() - 3, currentTimeMillisStr.length());
//                String timestampFormatStr = currentTimeMillis / 1000 + "." + millis;
//
//                printWriter.append(timestampFormatStr).append("\002").append(messageArr[1]);
//                printWriter.append(System.lineSeparator());
//            }
//
//            long endTimestamp = System.currentTimeMillis();
//            long totalTimes = ((endTimestamp - startTimestamp) <= 0) ? 1L : endTimestamp - startTimestamp;
//            logger.info(">> end   print time: {}", getDateByDiv(endTimestamp, "yyyy-MM-dd HH:mm:ss.SSS"));
//            logger.info(">> total time: {} ms", totalTimes);
//            logger.info(">> speed: {} r/s ", totalNum / ((totalTimes / 1000 <= 0) ? 1L : (totalTimes / 1000)));
//        } catch (IOException e) {
//            logger.error(">>> 计算出现异常，打印异常栈:");
//            e.printStackTrace();
//        }
//    }
//
//    public static String getDateByDiv(Long timestamp, String format) {
//        return new SimpleDateFormat(format).format(new Date(timestamp));
//    }
//
//    public static String getStarterLogStr() {
//        return "\n" + "  _                 _____               _\n" +
//                " | |               |  __ \\             | |\n" +
//                " | |     ___   __ _| |__) | __ ___   __| |_   _  ___ ___ _ __\n" +
//                " | |    / _ \\ / _` |  ___/ '__/ _ \\ / _` | | | |/ __/ _ \\ '__|\n" +
//                " | |___| (_) | (_| | |   | | | (_) | (_| | |_| | (_|  __/ |\n" +
//                " |______\\___/ \\__, |_|   |_|  \\___/ \\__,_|\\__,_|\\___\\___|_|\n" +
//                "               __/ |\n" +
//                "              |___/";
//
//    }
//
//}
