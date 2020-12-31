package cn.tiakon.learn.java.core.cryptology;


import java.io.UnsupportedEncodingException;

/**
 * Byte : 字节. 数据存储的基本单位，比如移动硬盘1T ， 单位是byte
 * bit  : 比特, 又叫位. 一个位要么是0要么是1. 数据传输的单位 , 比如家里的宽带100MB，下载速度并没有达到100MB，一般都是12-13MB，那么是因为需要使用 100 / 8
 * <p>
 * last time   : 2020/12/28 12:19
 *
 * @author tiankai.me@gmail.com on 2020/12/28 12:19.
 */
public class Example01_ByteAndBit {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("===============  Ascii Code : a   ===============");
        printAsciiCode();
        System.out.println("===============  Ascii Code : 尚(UTF-8)   ===============");
        printBinaryStringByChineseWithUFT8();
        System.out.println("===============  Ascii Code : 尚(GBK)   ===============");
        printBinaryStringByChineseWithGBK();
    }

    public static void printAsciiCode() throws UnsupportedEncodingException {
        String a = "a";
        // a 英文，使用 UTF-8 编码格式 占 1 字节。
//        byte[] bytes = a.getBytes();
        // a 英文，使用 GBK 编码格式 占 1 字节。
        byte[] bytes = a.getBytes("GBK");
        for (byte b : bytes) {
            int c = b;
            // 打印发现byte实际上就是ascii码
            System.out.println(c);
            // 我们在来看看每个byte对应的bit，byte获取对应的bit
            String s = Integer.toBinaryString(c);
            // 01100001
            // 打印出来应该是8个bit，但前面是0，没有打印 ，从打印结果可以看出来，一个英文字符 ，占一个字节
            System.out.println(s);
        }
    }

    /**
     * 中文在GBK编码下, 占据2个字节
     * 中文在UTF-8编码下, 占据3个字节
     * last time   : 2020/12/28 12:18
     *
     * @author tiankai.me@gmail.com on 2020/12/28 12:18.
     */
    public static void printBinaryStringByChineseWithUFT8() {
        String a = "尚";
        byte[] bytes = a.getBytes();
        for (byte b : bytes) {
            System.out.print(b + "   ");
            String s = Integer.toBinaryString(b);
            System.out.println(s);
        }
    }

    public static void printBinaryStringByChineseWithGBK() throws UnsupportedEncodingException {
        String a = "尚";
        // 在中文情况下，不同的编码格式，对应不同的字节
        //GBK :编码格式占2个字节
        // UTF-8：编码格式占3个字节
        byte[] bytes = a.getBytes("GBK");
        for (byte b : bytes) {
            String s = Integer.toBinaryString(b);
            System.out.println(b + "   " + s);
        }
    }
}
