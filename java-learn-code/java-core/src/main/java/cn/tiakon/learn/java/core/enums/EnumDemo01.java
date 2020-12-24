package cn.tiakon.learn.java.core.enums;

/**
 * Project java-learn-code
 * Package cn.tiakon.learn.java.core.enumdemo
 * email : tiankai.me@gmail.com
 *
 * @author Created by Tiakon on 2018/9/20 10:00.
 */

enum NovelSite {
    KANSHUZHONG, BIQUGE, QIDAIN, HONGXIU
}

/**
 * 方法说明：
 * novelSite.name()         返回 enum 实例声明时的名称
 * NovelSite.values()       返回 enum 实例的数组
 * novelSite.ordinal()      返回 enum 实例声明时次序
 * novelSite.getDeclaringClass()    返回 enum 实例所属的 enum 实例
 * Enum.valueOf(NovelSite.class, enumStr)       输入 enum 实例声明时的名称，返回一个 enum 实例
 */
public class EnumDemo01 {
    public static void main(String[] args) {
        System.out.println("----------------------");
        for (NovelSite novelSite : NovelSite.values()) {
            System.out.println("------- 【 枚举对象所在的位置 】-------" + novelSite.ordinal());
            System.out.println("------- 【 枚举对象所声明 】--------" + novelSite.getDeclaringClass());
        }

        for (String enumStr : "KANSHUZHONG, BIQUGE, QIDAIN, HONGXIU".replaceAll("[,] ", "\t").split("\t")) {
//            NovelSite novelSite = NovelSite.valueOf(enumStr);
            NovelSite novelSite = Enum.valueOf(NovelSite.class, enumStr);
            System.out.println("------- 【 枚举对象的名称 】--------" + novelSite.name());
        }
        System.out.println("----------【 END 】------------");
    }
}
