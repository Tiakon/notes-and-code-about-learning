package cn.tiakon.learn.java.concurrency.imooc.jimin.publish;


import cn.tiakon.learn.java.core.annotation.NonThreadSafe;
import cn.tiakon.learn.java.core.annotation.NotRecommend;
import lombok.extern.slf4j.Slf4j;

/**
 * 当一个对象还没有构造完成时，就时它被其它线程所见。
 *
 * @author Administrator
 */
@Slf4j
@NonThreadSafe
@NotRecommend
public class Escape {

    private int thisCanBeEscape = 0;

    public Escape() {
        new InnterClass();
    }

    private class InnterClass {

        public InnterClass() {
            // this 引用溢出
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
