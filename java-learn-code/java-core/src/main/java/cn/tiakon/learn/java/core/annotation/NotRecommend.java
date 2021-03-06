package cn.tiakon.learn.java.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记 【不推荐】 的类或写法。
 * <p>
 * Java注解详解
 * https://www.cnblogs.com/gqzdev/p/11667253.html
 *
 * @author Administrator
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface NotRecommend {
    String value() default "";
}
