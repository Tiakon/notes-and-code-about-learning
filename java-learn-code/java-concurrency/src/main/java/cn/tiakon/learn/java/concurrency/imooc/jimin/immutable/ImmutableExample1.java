package cn.tiakon.learn.java.concurrency.imooc.jimin.immutable;

import cn.tiakon.learn.java.core.annotation.NonThreadSafe;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@NonThreadSafe
@Slf4j
public class ImmutableExample1 {

    private final static int a = 1;
    private final static String b = "2";
    private final static Map<String, String> maps = Maps.newHashMap();

    static {
        maps.put("1", "1");
        maps.put("2", "2");
        maps.put("3", "3");
    }

    public static void main(String[] args) {
        // error
//        a = 2;
//        b = "3";
//        maps = Maps.newHashMap();

        maps.put("2", "4");
        log.info("map[key:2 ,value:{}]", maps.get("2"));

    }

}
