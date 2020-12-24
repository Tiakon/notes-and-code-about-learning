package cn.tiakon.learn.java.jvm.chapter01;

public class Tital_5_1_2 {
}

// default=>18014
// -Xss5M => 106330
class StackTest {
    private int count = 0;

    public void recursion() {
        count++;
        recursion();
    }

    public static void main(String[] args) {
        StackTest stackTest = new StackTest();
        try {
            stackTest.recursion();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(">> deep 0f stack is " + stackTest.count);
        }
    }
}