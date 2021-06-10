package cn.tiakon.learn.java.core.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Demo01 {

    public static void main(String[] args) {

        Map<String, Long> topics = new HashMap<>();

        for (Iterator<Map.Entry<String, Long>> it = topics.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Long> entry = it.next();
            long expireMs = entry.getValue();
            System.out.println("sss" + expireMs);
//            if (expireMs == TOPIC_EXPIRY_NEEDS_UPDATE)
//                entry.setValue(now + TOPIC_EXPIRY_MS);
//            else if (expireMs <= now) {
//                it.remove();
//                log.debug("Removing unused topic {} from the metadata list, expiryMs {} now {}", entry.getKey(), expireMs, now);
//            }
        }

        System.out.println(Demo01.toPositive(11250));

    }

    public static int toPositive(int number) {
        return number & 0x7fffffff;
    }

}


