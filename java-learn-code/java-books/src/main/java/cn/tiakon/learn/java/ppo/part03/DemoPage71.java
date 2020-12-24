package cn.tiakon.learn.java.ppo.part03;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;

/**
 * 对字符串分隔与查找的测试
 * last time   : 2020/7/13 15:21
 *
 * @author tiankai.me@gmail.com on 2020/7/13 15:21.
 */
public class DemoPage71 {

    // split() 对正则表达式的支持
    @Test
    public void SpiltRegexTest01() {

        String testStr = "a,b:c,d;";

        String[] strArr = testStr.split("[;|,|:]");

        for (String s : strArr) {
            System.out.println(s);
        }

    }

    // split() 的性能测试
    @Test
    public void SpiltRegexTest02() throws FileNotFoundException {
        String inputStr = "data\\test-data.gz";
        File file = new File(inputStr);

        try (FileInputStream fileInputStream = new FileInputStream(file);
             GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream)) {
            List<String> list = IOUtils.readLines(gzipInputStream, "UTF-8");

            long currentTimeMillis = System.currentTimeMillis();
            for (String line : list) {
//                StringSplitUnilts.split(line);
//                StringSplitUnilts.strTokenizer(line);
                StringSplitUnilts.indexofAndSubstring(line);
            }
            System.out.println(">> ".concat((System.currentTimeMillis() - currentTimeMillis) + " ms"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    // 字符串累加的性能测试
    @Test
    public void StringSumTest01() {
        String result = "";
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            // >> 24257 ms
//            result += i;
            // >> 21045 ms
//            result=result+i;
//             >> 6755 ms
            result = result.concat(String.valueOf(i));
        }
        System.out.println(">> ".concat((System.currentTimeMillis() - currentTimeMillis) + " ms"));
    }
}

/**
 * 提供字符串切割的工具类
 * last time   : 2020/7/13 16:09
 *
 * @author tiankai.me@gmail.com on 2020/7/13 16:09.
 */
class StringSplitUnilts {
// 同时处理42w条日志所花费的时间

    //>> 1515 ms
    static void split(String line) {
        line.split("[\002]");
    }

    // >> 1080 ms
    static void strTokenizer(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, "[\002]");
        while (tokenizer.hasMoreTokens()) {
            tokenizer.nextToken();
//            String token = tokenizer.nextToken();
//            System.out.println(token);
        }
    }

    // >> 4201 ms
    static void indexofAndSubstring(String line) {
        String temp = line;
        while (true) {
            int i = temp.indexOf("\002");
            if (i < 0) {
                break;
            }
            String substring = temp.substring(0, i);
            System.out.println(substring);
//            temp.substring(0, i);
            temp = temp.substring(i + 1);
        }
    }

    public static void main(String[] args) {
//        StringSplitUnilts.strTokenizer("v3.2\u00021591102854.000\u00020.097\u00021591102853.903\u00020.097\u0002112.132.208.53\u00022408:84e1:121:54ca:84ac:a4ff:fe6f:1557\u0002TCP_MISS\u0002200\u00021007\u00021353\u00021351\u00021346\u0002GET\u0002text/html;charset=GBK\u00021.1\u0002HTTPS\u0002www.cmpay.com\u0002/user/service/GetMblnoRsaSign.xhtml?viewCode=json&PRE_SIGN=3000118828486SFD0B9F1SF1CSHKEES1PGC78VFFM9I020200602210052861B8REUFLNLSUFRPPU8QS4TN5U7L70MK871.2\u0002722949242\u0002-\u0002M\u0002-\u000214110\u000211986\u00024527\u0002111.177.6.35\u0002172.17.254.164:8000\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u000248199\u0002443\u0002603\u0002\"OK\"\u0002-\u0002-\u0002-\u0002\"https://www.cmpay.com/info/version3/mkm/explain_139Mail/index.html\"\u0002\"Mozilla/5.0 (Linux; Android 9; V1824BA Build/PKQ1.181216.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.84 Mobile Safari/537.36 VivoBrowser/7.8.10.4\"\u0002-\n");
        StringSplitUnilts.indexofAndSubstring("v3.2\u00021591102854.000\u00020.097\u00021591102853.903\u00020.097\u0002112.132.208.53\u00022408:84e1:121:54ca:84ac:a4ff:fe6f:1557\u0002TCP_MISS\u0002200\u00021007\u00021353\u00021351\u00021346\u0002GET\u0002text/html;charset=GBK\u00021.1\u0002HTTPS\u0002www.cmpay.com\u0002/user/service/GetMblnoRsaSign.xhtml?viewCode=json&PRE_SIGN=3000118828486SFD0B9F1SF1CSHKEES1PGC78VFFM9I020200602210052861B8REUFLNLSUFRPPU8QS4TN5U7L70MK871.2\u0002722949242\u0002-\u0002M\u0002-\u000214110\u000211986\u00024527\u0002111.177.6.35\u0002172.17.254.164:8000\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u0002-\u000248199\u0002443\u0002603\u0002\"OK\"\u0002-\u0002-\u0002-\u0002\"https://www.cmpay.com/info/version3/mkm/explain_139Mail/index.html\"\u0002\"Mozilla/5.0 (Linux; Android 9; V1824BA Build/PKQ1.181216.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.84 Mobile Safari/537.36 VivoBrowser/7.8.10.4\"\u0002-\n");
    }


}
