package cn.tiakon.learn.java.jvm.classloader2;

public class Sample {
    private int v1 = 1;
    public Sample(){
        System.out.println("Sample is loaded by:" + this.getClass().getClassLoader());
        new Dog();
    }
}

