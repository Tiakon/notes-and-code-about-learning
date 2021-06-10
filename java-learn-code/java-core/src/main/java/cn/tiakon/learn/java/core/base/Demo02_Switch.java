package cn.tiakon.learn.java.core.base;

public class Demo02_Switch {
    public static void main(String[] args) {

        String expression = "c";
        switch (expression) {
            case "a":
                a();
                break;
            case "b":
                b();
                break;
            case "c":
                c();
                break;
            default:
                d();
        }

    }



    private static void a() {
        System.out.println("a");
    }

    private static void b() {
        System.out.println("b");
    }

    private static void c() {
        System.out.println("c");
    }

    private static void d() {
        System.out.println("d");
    }

}
