package cn.tiakon.learn.java.multithread.chapter05;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo03_MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String name;

    public Demo03_MyUncaughtExceptionHandler(String name) {
        this.name = name;
    }

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.WARNING, name.concat(" ").concat(t.getName()).concat(" 线程异常终止..."), e);
    }
}
