package cn.tiakon.learn.java.concurrency.imooc.jimin.immutable;

import cn.tiakon.learn.java.core.annotation.ThreadSafe;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
/**
 *
 * 创建不可变对象。
 *
 * */
@Slf4j
@ThreadSafe
public class ImmutableExample3 {

    private final static ImmutableList<Integer> immutableList = ImmutableList.of(1, 2, 3);

    private final static ImmutableSet immutableSet = ImmutableSet.copyOf(immutableList);

    private final static Map<Integer, Integer> map = ImmutableMap.of(1, 1);

    private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder().put(1, 1).put(2, 2).put(3, 3).build();

    public static void main(String[] args) {
        System.out.println(map2.get(3));
    }

}
