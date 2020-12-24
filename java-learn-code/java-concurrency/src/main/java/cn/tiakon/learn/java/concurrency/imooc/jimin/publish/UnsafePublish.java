package cn.tiakon.learn.java.concurrency.imooc.jimin.publish;

import cn.tiakon.learn.java.core.annotation.NonThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@NonThreadSafe
public class UnsafePublish {

    private String[] states = {"a", "b", "c"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {

        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("states: {}", Arrays.toString(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "b";
        log.info("states: {}", Arrays.toString(unsafePublish.getStates()));
    }

}
