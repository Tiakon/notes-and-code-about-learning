package cn.tiakon.learn.java.core.compression.zip;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://github.com/srikanth-lingala/zip4j
 * last time   : 2021/2/1 14:35
 *
 * @author tiankai.me@gmail.com on 2021/2/1 14:35.
 */
public class CreateZipFileUtilsExample {

    private static final Logger logger = LoggerFactory.getLogger(CreateZipFileUtilsExample.class);

    public static void main(String[] args) {

//        CreatePasswordProtectedZipExample();



    }

    public static void CreatePasswordProtectedZipExample() {
        String[] filename = {"a", "b", "c", "d"};
        for (String name : filename) {
            try {
                new ZipFile("R:\\data\\compression\\a.zip").addFile("R:\\data\\compression\\".concat(name));
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }

    }

}
