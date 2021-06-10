package cn.tiakon.learn.java.core.classReflection;



import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

public class ReflectionsTools02 {
    private static final Reflections reflections;

    static {
        //如果不加filterInputsBy，那么会扫描classpath,获取以后扫描门路所在我的项目的所有包
        reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("cn.tiakon")//指定扫描门路
                .filterInputsBy(new FilterBuilder().excludePackage("sqlite")) //排除某个包，留神不能是扫描包子包，否则不失效
                .setScanners(new MethodParameterScanner())// 增加办法参数扫描工具，能够依据须要增加多个扫描工具
        );
    }
    //Reflections reflections = new Reflections("my.package");
    public static void main(String[] args) {
        // 1、依据办法参数，反射获取扫描门路下所有匹配的办法
        Set methodsMatchParams = reflections.getMethodsMatchParams(String.class);
        methodsMatchParams.forEach(System.out::println);
    }
}

