package cn.tiakon.learn.java.core.classReflection;

import org.junit.Test;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.lang.annotation.Native;
import java.lang.reflect.*;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;


/**
 * Reflections通过扫描classpath，索引元数据，并且允许在运行时查询这些元数据。
 * <p>
 * 使用Reflections可以很轻松的获取以下元数据信息：
 * <p>
 * 获取某个类型的所有子类；比如，有一个父类是TestInterface，可以获取到TestInterface的所有子类。
 * 获取某个注解的所有类型/字段变量，支持注解参数匹配。
 * 使用正则表达式获取所有匹配的资源文件
 * 获取特定签名方法。
 */
public class ReflectionsTools01 {

    String SCAN_PKG = "";

    // 1.配置
    @Test
    public void config01() {
        Reflections reflections = new Reflections("cn.tiakon");
        //获取List及其子类
        Set<Class<? extends List>> listImpls = reflections.getSubTypesOf(List.class);
        listImpls.forEach(System.out::println);
    }

    @Test
    public void config02() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        //扫描vip.mycollege.jdk.reflect包
        Collection<URL> scannerPkg = ClasspathHelper.forPackage("cn.tiakon.learn.java.core.jdbc");
        SubTypesScanner subTypesScanner = new SubTypesScanner();

        //注解扫描
        TypeAnnotationsScanner typeAnnotationsScanner = new TypeAnnotationsScanner();
        FilterBuilder filter = new FilterBuilder()
                .includePackage("cn.tiakon.learn.java.core.jdbc")//注解扫描包
                .excludePackage("cn.tiakon.learn.java.core.jdbc.sqlite");//注解扫描扫描排除包
        typeAnnotationsScanner.filterResultsBy(filter);
        ConfigurationBuilder configuration = configurationBuilder
                .setUrls(scannerPkg)
                .setScanners(subTypesScanner, typeAnnotationsScanner);
        Reflections reflections = new Reflections(configuration);
    }

    // 2.通过类型扫描
    @Test
    public void newInstance() {
//        Reflections reflections = new Reflections(); // 扫描类路径
        Reflections reflections = new Reflections("java.util");   // 扫描指定类路径前缀
        //获取List及其子类
        Set<Class<? extends List>> listImpls = reflections.getSubTypesOf(List.class);
        listImpls.forEach(System.out::println);
    }

    // 3.通过注解扫描
    @Test
    public void methodAnnotationsScanner() {
        Reflections reflections = new Reflections(SCAN_PKG);
        //方法上有Deprecated注解
        Set<Method> resources = reflections.getMethodsAnnotatedWith(Deprecated.class);
        //有PostConstruct注解的构造方法
        Set<Constructor> injectables = reflections.getConstructorsAnnotatedWith(PostConstruct.class);
    }

    @Test
    public void FieldAnnotationsScanner() {
        //指定包下有Nonnegative的字段
        Reflections reflections = new Reflections(SCAN_PKG);
        Set<Field> ids = reflections.getFieldsAnnotatedWith(Nonnegative.class);
    }

    // 4.方法相关
    @Test
    public void methodParameterScanner() {
        Reflections reflections = new Reflections(SCAN_PKG);
        //参数类型是long和int
        Set<Method> someMethods = reflections.getMethodsMatchParams(long.class, int.class);
        //参数返回值是void
        Set<Method> voidMethods = reflections.getMethodsReturn(void.class);
        //任何参数上有注解Nullable
        Set<Method> pathParamMethods = reflections.getMethodsWithAnyParamAnnotated(Nullable.class);

        for (Method method : someMethods) {
            //获取方法参数的名称
            List<String> parameterNames = reflections.getMethodParamNames(method);
            parameterNames.forEach(System.out::println);
        }
    }

    // 5.工具类
    @Test
    public void reflectionUtils() {
        //必须是public方法
        Predicate<Method> publicPredicate = ReflectionUtils.withModifier(Modifier.PUBLIC);
        //有get前缀
        Predicate<Method> getPredicate = ReflectionUtils.withPrefix("get");
        //参数个数为0
        Predicate<Member> paramPredicate = ReflectionUtils.withParametersCount(0);
        Set<Method> methods = ReflectionUtils.getAllMethods(LinkedList.class, publicPredicate, getPredicate, paramPredicate);
        methods.forEach(method -> System.out.println(method.getName()));

        System.out.println("---------------");
        //参数必须是Collection及其子类
        Predicate<Member> paramsPredicate = ReflectionUtils.withParametersAssignableTo(Collection.class);
        //返回类型是boolean
        Predicate<Method> returnPredicate = ReflectionUtils.withReturnType(boolean.class);
        methods = ReflectionUtils.getAllMethods(LinkedList.class, paramsPredicate, returnPredicate);
        methods.forEach(method -> System.out.println(method.getName()));

        System.out.println("---------------");
        //字段有注解Native
        Predicate<Field> annotationPredicate = ReflectionUtils.withAnnotation(Native.class);
        //字段类型是int及其子类
        Predicate<Field> typeAssignablePredicate = ReflectionUtils.withTypeAssignableTo(int.class);
        Set<Field> fields = ReflectionUtils.getAllFields(Integer.class, annotationPredicate, typeAssignablePredicate);
//        Set<Field> fields = ReflectionUtils.getAllFields(Integer.class, annotationPredicate);
//        Set<Field> fields = ReflectionUtils.getAllFields(Integer.class, typeAssignablePredicate);
        fields.forEach(field -> System.out.println(field.getName()));
    }


}
