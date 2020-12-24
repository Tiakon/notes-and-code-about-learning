package cn.tiakon.learn.java.concurrency.imooc.jimin.singleton;


import cn.tiakon.learn.java.core.annotation.Recommend;
import cn.tiakon.learn.java.core.annotation.ThreadSafe;


/**
 * 枚举模式：最安全
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {

    private SingletonExample7() {
    }

    public SingletonExample7 getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    private enum SingletonEnum {
        INSTANCE;
        SingletonExample7 instance;

        // JVM 保证该方法只会调用一次。
        SingletonEnum() {
            instance = new SingletonExample7();
        }
        public SingletonExample7 getInstance() {
            return instance;
        }
    }
}
