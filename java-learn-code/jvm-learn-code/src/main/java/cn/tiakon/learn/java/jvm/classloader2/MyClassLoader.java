package cn.tiakon.learn.java.jvm.classloader2;

import java.io.*;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {
    private String name;

    private String path = "f:\\";

    private static final String fileType = ".class";

    public MyClassLoader(String name) {
        super();
        this.name = name;
        // 系统类加载器为该类的父类加载器
        System.out.println("MyClassLoader is loaded by " + this.getClass().getClassLoader());
    }

    public MyClassLoader(ClassLoader parent, String name) {
        // 明确自己的父类加载器
        super(parent);
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static String getFileType() {
        return fileType;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = this.loadClassData(name);
        return this.defineClass("cn.tiakon.learn.java.jvm.classloader2." + name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;

        try {
            this.name = this.name.replace(".", "\\");
            is = new FileInputStream(new File(path + name + fileType));
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = is.read())) {
                baos.write(ch);
            }
            data = baos.toByteArray();
            return data;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        System.out.println("读取字节码失败。");
        return null;
    }


    public static void test(ClassLoader loader) throws Exception {
        Class<?> classType = loader.loadClass("Dog");
        Object object = classType.newInstance();
        // 通过反射获取main函数
        Method main = classType.getMethod("main", String[].class);

        main.invoke(null, new Object[]{new String[]{"111","222"}});

    }

    public String toString() {
        return this.name;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("R:\\code\\source-code\\tiakon\\notes-and-code-about-learning\\java-learn-code\\jvm-learn-code\\target\\classes\\cn\\tiakon\\learn\\java\\jvm\\classloader2\\");
        test(loader1);

    }

}
