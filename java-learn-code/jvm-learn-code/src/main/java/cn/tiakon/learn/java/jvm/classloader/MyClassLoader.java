package cn.tiakon.learn.java.jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
    private String name;

    private String path = "f:\\";

    private static final String fileType = ".class";

    public MyClassLoader(String name) {
        super();
        this.name = name;
        // 系统类加载器为该类的父类加载器
        System.out.println("MyClassLoader is loaded by "+this.getClass().getClassLoader());
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
        return this.defineClass("cn.tiakon.learn.java.jvm.classloader."+name, b, 0, b.length);
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
        Class classType = loader.loadClass("Sample");
        Object object = classType.newInstance();
    }

    public String toString() {
        return this.name;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("R:\\code\\source-code\\tiakon\\notes-and-code-about-learning\\java-learn-code\\jvm-learn-code\\target\\classes\\cn\\tiakon\\learn\\java\\jvm\\classloader\\");

        MyClassLoader loader2 = new MyClassLoader(loader1, "loader2");
        loader2.setPath("R:\\code\\source-code\\tiakon\\notes-and-code-about-learning\\java-learn-code\\jvm-learn-code\\target\\classes\\cn\\tiakon\\learn\\java\\jvm\\classloader\\");

        test(loader2);

        //父加载器为根加载器
        MyClassLoader loader3 = new MyClassLoader(null, "loader3");
        loader3.setPath("R:\\code\\source-code\\tiakon\\notes-and-code-about-learning\\java-learn-code\\jvm-learn-code\\target\\classes\\cn\\tiakon\\learn\\java\\jvm\\classloader\\");
//        loader3.setPath("R:\\code\\source-code\\tiakon\\notes-and-code-about-learning\\java-learn-code\\jvm-learn-code\\target\\classes\\");

        test(loader3);

    }

}
