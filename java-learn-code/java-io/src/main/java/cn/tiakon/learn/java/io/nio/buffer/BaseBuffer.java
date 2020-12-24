package cn.tiakon.learn.java.io.nio.buffer;

import java.nio.IntBuffer;

/**
 * @author Administrator
 */
public class BaseBuffer {
    public static void main(String[] args) {



        IntBuffer buffer = IntBuffer.allocate(5);
        for (int i = 0; i < 3; i++) {
            buffer.put(i * 2);
        }
        /**
         *
         * public final Buffer flip() {
         *         limit = position;
         *         position = 0;
         *         mark = -1;
         *         return this;
         *     }
         *
         * */
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }



    }
}

