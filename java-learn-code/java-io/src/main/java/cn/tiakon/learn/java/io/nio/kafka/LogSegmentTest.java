package cn.tiakon.learn.java.io.nio.kafka;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;

public class LogSegmentTest {

    public static void main(String[] args) {
//        createTempFile();
        multiParam("1", "2", "3");
    }

    private static void createTempFile() {
        String dir = "C:\\Users\\Administrator\\AppData\\Local\\Temp\\kafka-5799930669442127548";
        Long offset = 40L;
        String IndexFileSuffix = ".index";
        File file = new File(dir, filenamePrefixFromOffset(offset) + IndexFileSuffix);
        try {
            FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String filenamePrefixFromOffset(Long offset) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(20);
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);
        return nf.format(offset);
    }

    private static void multiParam(String... str) {
        for (String s : str) {
            System.out.println(s);
        }
        System.out.println(str);
    }
}
