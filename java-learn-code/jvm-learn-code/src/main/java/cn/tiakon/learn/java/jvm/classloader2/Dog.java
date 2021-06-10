package cn.tiakon.learn.java.jvm.classloader2;

import sun.reflect.Reflection;

public class Dog {
    public Dog() {
        System.out.println("Dog    is loaded by:" + this.getClass().getClassLoader());
    }

    public static void main(String[] args) {

//        System.out.println("DOG Main...");
        System.setProperty("testProperties", "Dog is a good dog");

        System.out.println(System.getProperty("test"));

        // 当前线程类加载器
        System.out.println(Thread.currentThread().getContextClassLoader() + "DOG ContextCLassLoader start");

        // 新建自定义类加载器
        MyClassLoader loader1 = new MyClassLoader("loader1");

        loader1.setPath("R:\\code\\source-code\\tiakon\\notes-and-code-about-learning\\java-learn-code\\jvm-learn-code\\target\\classes\\cn\\tiakon\\learn\\java\\jvm\\classloader2\\");

        //设置当前线程类加载器为自定义类加载器，其父类加载器是 app类加载器
        Thread.currentThread().setContextClassLoader(loader1);

        try {
            // 用当前类的类加载器去加载类
            test(Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getContextClassLoader() + "DOG ContextCLassLoader end");

        Cpu cpu = new Cpu();
        cpu.aa();
        Thread thread = new Thread(cpu);
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void test(ClassLoader loader) throws Exception {
        Class classType = loader.loadClass("cn.tiakon.learn.java.jvm.classloader2.Sample");
        Object object = classType.newInstance();
        // 新建一个Dog对象，查看其加载器
        Dog dog = new Dog();
    }


}

class Cpu implements Runnable {

    public Cpu() {
        System.out.println("Cpu    is loaded by:" + this.getClass().getClassLoader());
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(System.getProperty("testProperty"));
    }

    public void aa() {
        System.out.println(Reflection.getCallerClass());                     // 获取调用者的类
        System.out.println(Reflection.getCallerClass(1));                 // 获取这句话所在的自身的类
        System.out.println(Reflection.getCallerClass().getClassLoader());    //  获取调用者的类加载器
        System.out.println(Reflection.getCallerClass(-1));                 // 所在类的类加载器
    }
}


