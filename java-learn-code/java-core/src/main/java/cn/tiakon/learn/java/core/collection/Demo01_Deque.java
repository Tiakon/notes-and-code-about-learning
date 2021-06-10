package cn.tiakon.learn.java.core.collection;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;

public class Demo01_Deque {

    public static void main(String[] args) {
        Deque<ByteBuffer> free = new ArrayDeque<ByteBuffer>();
        System.out.println(free.size());
        System.out.println(free.isEmpty());
    }

}
