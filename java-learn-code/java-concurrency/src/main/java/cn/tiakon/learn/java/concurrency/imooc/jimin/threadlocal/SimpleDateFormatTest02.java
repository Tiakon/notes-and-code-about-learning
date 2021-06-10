package cn.tiakon.learn.java.concurrency.imooc.jimin.threadlocal;

import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Administrator
 */
public class SimpleDateFormatTest02 {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<String> dateStrList = Lists.newArrayList("2018-04-01 10:00:01", "2018-04-02 11:00:02",
                "2018-04-03 12:00:03", "2018-04-04 13:00:04", "2018-04-05 14:00:05");

        for (String str : dateStrList) {
            executorService.execute(() -> {
                try {
                    simpleDateFormat.parse(str);
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }
}


class SimpleDateFormatThreadLocal {

    public static void main(String[] args) {
        Supplier<ThreadLocal> threadLocal = () -> ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH));
        SimpleDateFormat df = (SimpleDateFormat)threadLocal.get().get();
        System.out.println(df.format(new Date()));
    }

}