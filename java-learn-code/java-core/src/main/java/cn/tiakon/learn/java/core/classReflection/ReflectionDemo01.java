package cn.tiakon.learn.java.core.classReflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

interface Study {
    //仅为了演示获得接口,就没有写抽象方法
}

class Person {
    public String name;
    public int age;
    public Person() {
        super();
    }
    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }
    public String showInfo() {
        return "name=" + name + ", age=" + age;
    }
}

class Student extends Person implements Study {
    public String className;
    private String address;
    public Student() {
        super();
    }
    public Student(String name, int age, String className, String address) {
        super(name, age);
        this.className = className;
        this.address = address;
    }
    public Student(String className) {
        this.className = className;
    }
    public String toString() {
        return "姓名：" + name + ",年龄：" + age + ",班级：" + className + ",住址："
                + address;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}

public class ReflectionDemo01 {

    public static void main(String[] args) {
        Class stu = null;
        try {
            stu = Class.forName("cn.tiakon.learn.java.core.classReflection.Student");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 获取对象的所有公有属性。
        Field[] fields = stu.getFields();
        for (Field f : fields) {
            System.out.println(f);
        }
        System.out.println("---------------------");

        // 获取对象所有属性，但不包含继承的。
        Field[] declaredFields = stu.getDeclaredFields();
        for (Field ff : declaredFields) {
            System.out.println(ff);
        }

        // 获取对象的所有公共方法
        Method[] methods = stu.getMethods();
        for (Method m : methods) {
            System.out.println(m);
        }
        System.out.println("---------------------");

        // 获取对象所有方法，但不包含继承的
        Method[] declaredMethods = stu.getDeclaredMethods();
        for (Method ms : declaredMethods) {
            System.out.println(ms);
        }

        // 获取对象所有的公共构造方法
        Constructor[] constructors = stu.getConstructors();
        for (Constructor c : constructors) {
            System.out.println(c);
        }
        System.out.println("---------------------");

        // 获取对象所有的构造方法
        Constructor[] declaredConstructors = stu.getDeclaredConstructors();
        for (Constructor con : declaredConstructors) {
            System.out.println(con);
        }

        System.out.println(stu.getName());//获取对象全限定名称
        System.out.println(stu.getPackage());// 获取包名
        Class[] interfaces = stu.getInterfaces();//获取该类实现的所有接口
        for (Class in : interfaces) {
            System.out.println(in);
        }
        System.out.println("---------------------");

        Student stu1 = null;
        try {
            stu1 = (Student) stu.newInstance();

            // 第一种方法，实例化默认构造方法，调用set赋值
            stu1.setAddress("深圳南山");
            System.out.println(stu1);

            // 第二种方法 取得全部的构造函数 使用构造函数赋值
            Constructor<Student> constructor = stu.getConstructor(String.class, int.class, String.class, String.class);
            Student stu2 = (Student) constructor.newInstance("李四", 18, "七班", "深圳");
            System.out.println(stu2);

            // 獲取方法并执行方法
            Method show = stu.getMethod("showInfo");//获取showInfo()方法
            Object object = show.invoke(stu2);//调用showInfo()方法

            System.out.println(object);

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
