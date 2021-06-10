package cn.tiakon.learn.java.core.jmx.entity;

import java.util.concurrent.atomic.AtomicLong;

public class Alarm implements AlarmMBean {

    private final AtomicLong alarm = new AtomicLong(0);

    @Override
    public Integer getAlarm() {
        return alarm.intValue();
    }

    @Override
    public void setAlarm() {
        alarm.incrementAndGet();
    }

}
