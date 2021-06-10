package cn.tiakon.learn.java.io.nio.kafka;

import java.util.Locale;

public final class OperatingSystem {

    private OperatingSystem() {
    }

    public static final String NAME;

    public static final boolean IS_WINDOWS;

    static {
        NAME = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        IS_WINDOWS = NAME.startsWith("windows");
    }
}