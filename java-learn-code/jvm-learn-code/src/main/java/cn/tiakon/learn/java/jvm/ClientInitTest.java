package cn.tiakon.learn.java.jvm;

/**
 * 反编译
 * javap -v Demo01.class
 * <p>
 * 类加载子系统：
 * <p>
 * 1.加载(load)
 * 2.链接(link)
 * 2.1 校验操作(verify)
 * 2.2 预备(prepare)
 * 2.3 解析(Resolve)
 * 3.初始化(init)
 *
 * @author Administrator
 */
public class ClientInitTest {

    private static int k = 3;

    static {
        k = 2;
        m = 4;
        System.out.println(k);
        // 非法的前向引用
//        System.out.println(m);
    }

    // link阶段，先对类变量进行了默认值赋值操作，之后才是初始化阶段，按代码中的顺序赋值。
    private static int m = 5;

    public static void main(String[] args) {
        System.out.println(ClientInitTest.k);
        System.out.println(ClientInitTest.m);
    }
}
