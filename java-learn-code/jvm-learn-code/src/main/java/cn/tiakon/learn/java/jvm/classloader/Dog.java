package cn.tiakon.learn.java.jvm.classloader;

public class Dog {
    public Dog(){
        System.out.println("Dog    is loaded by:" + this.getClass().getClassLoader());
    }
}
