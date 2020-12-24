package cn.tiakon.learn.java.io.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * FileChannel 类
 * <p>
 * FileChannel主要用来对本地文件进行 IO 操作，常见的方法有
 * <p>
 * <p>
 * public int read(ByteBuffer dst) ，从通道读取数据并放到缓冲区中
 * public int write(ByteBuffer src) ，把缓冲区的数据写到通道中
 * public long transferFrom(ReadableByteChannel src, long position, long count)，从目标通道中复制数据到当前通道
 * public long transferTo(long position, long count, WritableByteChannel target)，把数据从当前通道复制给目标通道
 *
 * @author Administrator
 */
public class NioFileChannelDemo01 {
    public static void main(String[] args) throws IOException {
        // UTF-8 编码中 一个中文字符 = 3个字节
//        writeLocalFile();
//        readLocalFile();
        copyLocalFile();
//        copyLoclImageFile();
    }

    private static void writeLocalFile() {
        try {
            // UTF-8 编码中 一个中文字符 = 3个字节
            String result = "你好，尚硅谷！";
            FileOutputStream fileOutputStream = new FileOutputStream(new File("NioFileChannelDemo01.txt").getCanonicalFile());
            FileChannel fileChannel = fileOutputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(result.getBytes());
            buffer.flip();
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readLocalFile() {
        try {
            File file = new File("NioFileChannelDemo01.txt");
            FileChannel fileChannel = new FileInputStream(file.getCanonicalFile()).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            fileChannel.read(buffer);
            buffer.flip();
//            Charset charset = Charset.forName("UTF-8");
//            System.out.println(charset.decode(buffer));

            String result = new String(buffer.array());
            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyLocalFile() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("图片1.png").getCanonicalFile());
            FileChannel inputFileChannel = fileInputStream.getChannel();
            fileOutputStream = new FileOutputStream(new File("图片5.png").getCanonicalFile());
            FileChannel outputfileChannel = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (inputFileChannel.read(buffer) != -1) {
                buffer.flip();
                outputfileChannel.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fileInputStream);
            close(fileOutputStream);
        }

    }

    private static void copyLoclImageFile() {
        try {
            FileChannel inputFileChannel = new FileInputStream(new File("图片1.png").getCanonicalFile()).getChannel();
            FileChannel outputfileChannel = new FileOutputStream(new File("图片2.png").getCanonicalFile()).getChannel();
            FileChannel outputfileChannel2 = new FileOutputStream(new File("图片3.png").getCanonicalFile()).getChannel();

            long transferFromed = 0;
            long inputFileChannelSize = inputFileChannel.size();
            while (transferFromed != inputFileChannelSize) {
                // 从目标通道中复制数据到当前通道
                transferFromed += outputfileChannel.transferFrom(inputFileChannel, 0, inputFileChannelSize);
            }

            long transferToed = 0;
            while (transferToed != inputFileChannelSize) {
                // 把数据从当前通道复制给目标通道
                transferToed += inputFileChannel.transferTo(0, inputFileChannelSize, outputfileChannel2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
