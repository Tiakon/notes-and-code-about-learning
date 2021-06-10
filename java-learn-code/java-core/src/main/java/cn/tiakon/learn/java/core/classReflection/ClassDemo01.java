package cn.tiakon.learn.java.core.classReflection;

import java.io.Serializable;

// https://blog.csdn.net/shikangkai/article/details/50273527
public class ClassDemo01 {

    public static void main(String[] args) {
        // 使用 instanceof 进行类型推断
        // 使用 instanceof 不仅可以判断对象是否是某个类的实例，甚至连该类继承的父类和实现的接口也都能够被识别为 true
        instanceofUsage();
        System.out.println("==================================");
        // 精确判断一个对象是否是具体某个类的实例
        classEqualsUsage();
    }

    /**
     * public abstract class Number implements java.io.Serializable {}
     * public final class Integer extends Number implements Comparable<Integer> {}
     * <p>
     * Integer i is a Number
     * Integer i is a Serializable
     * Integer i is an Integer
     * Integer i isn't a Float
     * <p>
     * last time   : 2021/1/29 12:00
     *
     * @author tiankai.me@gmail.com on 2021/1/29 12:00.
     */
    public static void instanceofUsage() {
        Object i = new Integer(7);
        if (i instanceof Number) {
            System.out.println("Integer i is a Number");
        } else {
            System.out.println("Integer i isn't a Number");
        }
        if (i instanceof Serializable) {
            System.out.println("Integer i is a Serializable");
        } else {
            System.out.println("Integer i isn't a Serializable");
        }
        if (i instanceof Integer) {
            System.out.println("Integer i is an Integer");
        } else {
            System.out.println("Integer i isn't an Integer");
        }
        if (i instanceof Float) {
            System.out.println("Integer i is a Float");
        } else {
            System.out.println("Integer i isn't a Float");
        }
    }

    public static void classEqualsUsage() {
        Object i = new Integer(7);
        if (i.getClass().equals(Number.class)) {
            System.out.println("Integer i is a Number");
        } else {
            System.out.println("Integer i isn't a Number");
        }

        if (i.getClass().equals(Serializable.class)) {
            System.out.println("Integer i is a Serializable");
        } else {
            System.out.println("Integer i isn't a Serializable");
        }

        if (i.getClass().equals(Integer.class)) {
            System.out.println("Integer i is an Integer");
            System.out.println(i.getClass().getName());
        } else {
            System.out.println("Integer i isn't an Integer");
        }

        if (i.getClass().equals(Float.class)) {
            System.out.println("Integer i is a Float");
        } else {
            System.out.println("Integer i isn't a Float");
        }
    }

}
