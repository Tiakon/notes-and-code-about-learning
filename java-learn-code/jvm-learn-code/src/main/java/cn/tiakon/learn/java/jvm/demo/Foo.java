package cn.tiakon.learn.java.jvm.demo;

/**
 * project : java-learn-code
 * package : cn.tiakon.learn.java.core.jvm
 * email : tiankai.me@gmail.com
 *
 * @author Created by Tiakon on 2018/10/10 23:02.
 */
public class Foo {
    public static void main(String[] args) {
        /*boolean flag = true;
        if (flag) {
            System.out.println("Hello , Java !!!");
        }

        if (flag == true) {
            System.out.println("Hello , Jvm !!!");
        }*/

        Foo3.getPrint();

        Foo2.getPrint();

    }
}

class Foo2 {

    private Foo2() {
        System.out.println("Hi , Foo2 !!!");
    }

    public static Foo2 instance = new Foo2();

    static Foo2 getInstance() {
        return instance;
    }

    static void getPrint() {
        System.out.println("Hello , Jvm !!!");
    }

}

class Foo3 {

    private Foo3() {
        System.out.println("Hello , Foo3 !!!");
    }

    private static class Foo4 {
        public static Foo3 instance = new Foo3();
    }

    public static Foo3 getInstance() {
        return Foo4.instance;
    }

    public static void getPrint() {
        System.out.println("Hi , Jvm !!!");
    }

}

