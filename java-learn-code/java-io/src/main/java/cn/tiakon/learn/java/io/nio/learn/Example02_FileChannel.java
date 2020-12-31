package cn.tiakon.learn.java.io.nio.learn;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
public class Example02_FileChannel {
    public static void main(String[] args) throws IOException {
        // UTF-8 编码中 一个中文字符 = 3个字节
//        writeLocalFile();
//        readLocalFile();
        copyLocalFileByBuffer();
//        copyLocalFileByChannel();
    }

    private static void writeLocalFile() throws IOException {
        // UTF-8 编码中 一个中文字符 = 3个字节
        String result = "你好，尚硅谷！";
        FileOutputStream fileOutputStream = new FileOutputStream(new File("NioFileChannelDemo01.txt").getCanonicalFile(), true);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(result.getBytes());
        buffer.flip();
        fileChannel.write(buffer);
        fileChannel.close();
        fileOutputStream.close();
    }

    private static void readLocalFile() throws IOException {
        File file = new File("NioFileChannelDemo01.txt");
        FileChannel fileChannel = new FileInputStream(file.getCanonicalFile()).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        fileChannel.read(buffer);
        buffer.flip();
//            Charset charset = Charset.forName("UTF-8");
//            System.out.println(charset.decode(buffer));
        String result = new String(buffer.array());
        System.out.println(result);
    }

    private static void copyLocalFileByBuffer() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\kafka-offset-monitor\\01.png").getCanonicalFile());
            FileChannel inputFileChannel = fileInputStream.getChannel();
            fileOutputStream = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\kafka-offset-monitor\\06.png").getCanonicalFile());
            FileChannel outputFileChannel = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (inputFileChannel.read(buffer) != -1) {
                buffer.flip();
                outputFileChannel.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fileInputStream);
            close(fileOutputStream);
        }

    }

    private static void copyLocalFileByChannel() {

        FileChannel inputFileChannel = null;
        FileChannel outputFileChannel = null;
        FileChannel outputFileChannel2 = null;

        try {
            inputFileChannel = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\kafka-offset-monitor\\01.png").getCanonicalFile()).getChannel();
            outputFileChannel = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\kafka-offset-monitor\\06.png").getCanonicalFile()).getChannel();
            outputFileChannel2 = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\kafka-offset-monitor\\07.png").getCanonicalFile()).getChannel();

            long readLength1 = 0;
            long inputFileChannelSize = inputFileChannel.size();
            while (readLength1 != inputFileChannelSize) {
                // 目标通道从当前通道读数据
                readLength1 += outputFileChannel.transferFrom(inputFileChannel, 0, inputFileChannelSize);
            }

            long readLength2 = 0;
            while (readLength2 != inputFileChannelSize) {
                // 当前通道复制数据到目标通道
                readLength2 += inputFileChannel.transferTo(0, inputFileChannelSize, outputFileChannel2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(inputFileChannel);
            close(outputFileChannel);
            close(outputFileChannel2);
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
