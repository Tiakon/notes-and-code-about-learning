package cn.tiakon.learn.java.core.cryptology;

import com.sun.org.apache.xml.internal.security.utils.Base64;


/**
 * Base64是网络上最常见的用于传输8Bit字节码的可读性编码算法之一
 * 可读性编码算法不是为了保护数据的安全性，而是为了可读性
 * 可读性编码不改变信息内容，只改变信息内容的表现形式
 * 所谓Base64，即是说在编码过程中使用了64种字符：大写A到Z、小写a到z、数字0到9、“+”和“/”
 * Base58是Bitcoin(比特币)中使用的一种编码方式，主要用于产生Bitcoin的钱包地址
 * 相比Base64，Base58不使用数字"0"，字母大写"O"，字母大写"I"，和字母小写"i"，以及"+"和"/"符号
 * last time   : 2020/12/28 13:49
 *
 * @author tiankai.me@gmail.com on 2020/12/28 13:49.
 */
public class Example03_Base64 {


    // base64 是 3个字节为一组，一个字节 8位，一共 就是24位 ，然后，把3个字节转成4组，每组6位，
    // 3 * 8 = 4 * 6 = 24 ，每组6位，缺少的2位，会在高位进行补0 ，这样做的好处在于 ，base取的是后面6位，去掉高2位 ，那么base64的取值就可以控制在0-63位了，
    // 所以就叫base64，111 111 = 32 + 16 + 8 + 4 + 2 + 1 =
    // 大家可能发现一个问题，咱们的base64有个 = 号，但是在映射表里面没有发现 = 号 ， 这个地方需要注意，等号非常特殊，因为base64是三个字节一组 ，如果当我们的位数不够的时候，会使用等号来补齐
    public static void main(String[] args) {
        //  1：MQ== 表示一个字节，不够三个字节，所以需要后面通过 == 号补齐
        System.out.println(Base64.encode("1".getBytes()));
        // MTI=
        System.out.println(Base64.encode("12".getBytes()));
        // MTIz
        System.out.println(Base64.encode("123".getBytes()));
        // 硅谷:中文占6个字节，6 * 8 = 48 ，刚刚好被整除，所以没有等号
        System.out.println(Base64.encode("硅谷".getBytes()));
    }
}
