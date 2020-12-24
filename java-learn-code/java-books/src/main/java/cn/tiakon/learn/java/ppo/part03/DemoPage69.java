package cn.tiakon.learn.java.ppo.part03;

import java.util.ArrayList;

/**
 * JDK1.6中 String subString() 方法的内存泄露问题。
 * last time   : 2020/7/13 14:34
 *
 * @author tiankai.me@gmail.com on 2020/7/13 14:34.
 */
public class DemoPage69 {
    public static void main(String[] args) {

        ArrayList<String> handler = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
//            HugeString h = new HugeString();
            ImprovedHugeString h = new ImprovedHugeString();
            handler.add(h.subString(1, 5));
        }

    }
}

class HugeString {
    private String s = new String(new char[100000]);

    public String subString(int startIndex, int endIndex) {
        return s.substring(startIndex, endIndex);
    }
}

class ImprovedHugeString {
    private String s = new String(new char[100000]);

    public String subString(int startIndex, int endIndex) {
        return new String(s.substring(startIndex, endIndex));
    }
}

