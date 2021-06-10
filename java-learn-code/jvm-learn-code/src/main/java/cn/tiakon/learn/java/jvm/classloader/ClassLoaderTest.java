package cn.tiakon.learn.java.jvm.classloader;

import sun.misc.Launcher;
import sun.net.spi.nameservice.dns.DNSNameService;

import java.net.URL;
import java.security.Provider;

public class ClassLoaderTest {

    public static void main(String[] args) {

        System.out.println("启动类加载器：");
        // 获取 BootstrapClassLoader 能够加载的 api 的路径.
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url.toExternalForm());
        }
        // 从上面的路径中找一个类，看看他的类加载器是什么？引导类加载器(获取不到，为null，是jvm的一部分)
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader); // null

        System.out.println("扩展类加载器：");
        String extDirs = System.getProperty("java.ext.dirs");

        for (String path : extDirs.split("[;]")) {
            System.out.println(path);
        }

        // 从上面的路径中找一个类，看看他的类加载器是什么？扩展类加载器
        ClassLoader classLoader1 = DNSNameService.class.getClassLoader();
        System.out.println(classLoader1);//sun.misc.Launcher$ExtClassLoader@1b6d3586

        // Java 核心类库都是使用引导类加载器进行加载
        System.out.println(String.class.getClassLoader());  // null

        // 对于用户自定义类来说：默认使用系统类加载器进行加载
        System.out.println(ClassLoaderTest.class.getClassLoader());  // sun.misc.Launcher$AppClassLoader@18b4aac2

        // 双亲委派机制 与 JDBC 驱动的注册的实现
    }

}
