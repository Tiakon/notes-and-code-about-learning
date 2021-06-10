package cn.tiakon.learn.java.core.lambda;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class Lambda01_FunctionInterface {
    public static void main(String[] args) {

        // lambda 表达式的不同形式：
        Runnable noArguments = () -> System.out.println("no arguments");
        ActionListener oneArguments = event -> System.out.println("no arguments");
        Runnable multiArguments = () -> {
            System.out.println("hello");
            System.out.println("world");
        };
        BinaryOperator<Long> add = (x, y) -> x + y;
        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;

        final String[] array = {"hello", "world"};

        // 引用值，而不是变量
        final String name = "hello,world";
        Runnable getName = () -> {
            System.out.println(name);
        };

        // 函数接口 与 类型推断
        // 1. T -> Predicate -> Boolean
        Predicate<Integer> atLeast5 = x -> x > 5;

        // 2. 没有泛型编译则不通过
        BinaryOperator<Long> addLongs = (x, y) -> x + y;

        /**
         *
         * 1. Lambda 表达式是一个匿名方法，将行为像数据一样进行传递。
         * 2. Lambda 表达式的常见结构：BinaryOperator<Long> addLongs = (x, y) -> x + y;
         * 3. 函数接口指仅具有单个抽象方法的接口，用来表示 Lambda 表达式的类型。
         *
         * */

        JButton button = new JButton();

        button.addActionListener(event ->
                System.out.println(event.getActionCommand())
        );
    }

}


