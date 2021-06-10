package cn.tiakon.learn.java.io.nio.copyfile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

interface FileCopyRunner {
    void copyFile(File source, File target);
}

/**
 * @author Administrator
 */
public class FileCopyBenchmarkTool {

    private static final int ROUNDS = 10;

    private static final int capacity = 1024 * 8;

    private static void benchmark(FileCopyRunner test, File source, File target) {
        long elapsed = 0L;
        for (int i = 0; i < ROUNDS; i++) {
            long startTime = System.currentTimeMillis();
            test.copyFile(source, target);
            elapsed = System.currentTimeMillis() - startTime;
            target.delete();
        }
        System.out.println(test + " : " + elapsed / ROUNDS);
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

    public static void main(String[] args) {

        FileCopyRunner noBufferStreamCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                InputStream fileInputStream = null;
                OutputStream fileOutputStream = null;
                try {
                    fileInputStream = new FileInputStream(source);
                    fileOutputStream = new FileOutputStream(target);

                    int result;
                    // 下一个数据字节；如果到达流的末尾，则返回-1。
                    while ((result = fileInputStream.read()) != -1) {
                        fileOutputStream.write(result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fileInputStream);
                    close(fileOutputStream);
                }

            }

            @Override
            public String toString() {
                return "noBufferStreamCopy";
            }
        };

        FileCopyRunner nioBufferCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                FileChannel fileIntputChannel = null;
                FileChannel fileOutputChannel = null;

                try {
                    fileIntputChannel = new FileInputStream(source).getChannel();
                    fileOutputChannel = new FileOutputStream(target).getChannel();

                    ByteBuffer buffer = ByteBuffer.allocate(capacity);

                    while (fileIntputChannel.read(buffer) != -1) {
                        // 切换成写模式；移动 postion、limit指针
                        buffer.flip();
                        // 确保buffer中的数据全部被写入 fileOutputChannel
                        while (buffer.hasRemaining()) {
                            fileOutputChannel.write(buffer);
                        }
                        // 确保 buffer 中的内容全部被读取后，使用clear 切换成读模式。
                        buffer.clear();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fileIntputChannel);
                    close(fileOutputChannel);
                }

            }

            @Override
            public String toString() {
                return "nioBufferCopy";
            }
        };

        FileCopyRunner bufferedStreamCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                InputStream fileInputStream = null;
                OutputStream fileOutputStream = null;
                try {

                    fileInputStream = new BufferedInputStream(new FileInputStream(source));
                    fileOutputStream = new BufferedOutputStream(new FileOutputStream(target));

                    byte[] buffer = new byte[capacity];

                    int readTotalNum;
                    // 读入缓冲区的字节总数，如果读到末尾没有更多数据则返回-1。
                    while ((readTotalNum = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, readTotalNum);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fileInputStream);
                    close(fileOutputStream);
                }

            }


            @Override
            public String toString() {
                return "bufferedStreamCopy";
            }

        };

        FileCopyRunner nioTransferCopy = new FileCopyRunner() {
            @Override
            public void copyFile(File source, File target) {
                FileChannel fileIntputChannel = null;
                FileChannel fileOutputChannel = null;

                try {
                    fileIntputChannel = new FileInputStream(source).getChannel();
                    fileOutputChannel = new FileOutputStream(target).getChannel();

                    long size = fileIntputChannel.size();
                    long transferred = 0L;
                    while (transferred != size) {
                        transferred += fileIntputChannel.transferTo(0, size, fileOutputChannel);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    close(fileIntputChannel);
                    close(fileOutputChannel);
                }
            }

            @Override
            public String toString() {
                return "nioTransferCopy";
            }
        };

        File smallFile = new File("R:\\data\\test\\file\\01.jpg");
        File smallFileCopy = new File("R:\\data\\test\\target\\01.jpg");
        System.out.println("---Copying small file---");
        benchmark(noBufferStreamCopy, smallFile, smallFileCopy);
        benchmark(bufferedStreamCopy, smallFile, smallFileCopy);
        benchmark(nioBufferCopy, smallFile, smallFileCopy);
        benchmark(nioTransferCopy, smallFile, smallFileCopy);

        File bigFile = new File("R:\\data\\test\\file\\02.jar");
        File bigFileCopy = new File("R:\\data\\test\\target\\02.jar");
        System.out.println("---Copying big file---");
//        benchmark(noBufferStreamCopy, bigFile, bigFileCopy);
        benchmark(bufferedStreamCopy, bigFile, bigFileCopy);
        benchmark(nioBufferCopy, bigFile, bigFileCopy);
        benchmark(nioTransferCopy, bigFile, bigFileCopy);

        File hugeFile = new File("R:\\data\\test\\file\\cdh-env-jars.tar.gz");
        File hugeFileCopy = new File("R:\\data\\test\\target\\cdh-env-jars.tar.gz");
        System.out.println("---Copying huge file---");
//        benchmark(noBufferStreamCopy, hugeFile, hugeFileCopy);
        benchmark(bufferedStreamCopy, hugeFile, hugeFileCopy);
        benchmark(nioBufferCopy, hugeFile, hugeFileCopy);
        benchmark(nioTransferCopy, hugeFile, hugeFileCopy);

    }


}
