package cn.tiakon.learn.java.core.base;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.cnblogs.com/lanxuezaipiao/p/3440471.html
 * <p>
 * 至少有两种情况下finally语句是不会被执行的：
 * <p>
 * （1）try语句没有被执行到，如在try语句之前就返回了，这样finally语句就不会执行，这也说明了finally语句被执行的必要而非充分条件是：相应的try语句一定被执行到。
 * <p>
 * （2）在try块中有System.exit(0);这样的语句，System.exit(0);是终止Java虚拟机JVM的，连JVM都停止了，所有都结束了，当然finally语句也不会被执行到。
 * <p>
 * 结论：
 * <p>
 * 1. finally语句在return语句执行之后return返回之前执行的。
 * 2. finally块中的return语句会覆盖try块中的return返回。
 * 3. 如果finally语句中没有return语句覆盖返回值，那么原来的返回值可能因为finally里的修改而改变也可能不变。
 * 4. try块里的return语句在异常的情况下不会被执行，这样具体返回哪个看情况。
 * 5. 当发生异常后，catch中的return执行情况与未发生异常时try中return的执行情况完全一样。
 */
public class Demo03_ReturnAndFinally {

    public static void main(String[] args) {
        System.out.println(test1());
        System.out.println(test2());
        System.out.println(test3());
        System.out.println(test3_2().get("KEY"));
        System.out.println(test4());
        System.out.println(test5());
    }

    public static String test1() {
        try {
            System.out.println("try block");
            return test1_2();
        } finally {
            System.out.println("finally block");
        }
    }

    public static String test1_2() {
        System.out.println("return statement");
        return "after return";
    }

    public static int test2() {
        int b = 20;
        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }
            return 200;
        }
        // return b;
    }

    public static int test3() {
        int b = 20;
        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }
            b = 150;
        }
        return 2000;
    }

    public static Map<String, String> test3_2() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("KEY", "INIT");
        try {
            map.put("KEY", "TRY");
            return map;
        } catch (Exception e) {
            map.put("KEY", "CATCH");
        } finally {
            map.put("KEY", "FINALLY");
            map = null;
        }
        return map;
    }

    public static int test4() {
        int b = 20;
        try {
            System.out.println("try block");
            b = b / 0;
            return b += 80;
        } catch (Exception e) {
            b += 15;
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }
            b += 50;
        }
        return 204;
    }

    public static int test5() {
        int b = 20;
        try {
            System.out.println("try block");
            b = b / 0;
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
            return b += 15;
        } finally {

            System.out.println("finally block");
            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }
            b += 50;
        }
        //return b;
    }

}
