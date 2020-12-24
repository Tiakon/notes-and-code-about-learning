package cn.tiakon.learn.java.jvm;

public class HeapAllo {
    public static void main(String[] args) {

        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        long totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;

        System.out.println("maxMemory :" + maxMemory + " MB");
        System.out.println("maxMemory :" + freeMemory + " MB");
        System.out.println("maxMemory :" + totalMemory + " MB");

    }
}
