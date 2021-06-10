package cn.tiakon.app.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.OptionalLong;

public class GetFileINode {
    public static void main(String[] args) throws IOException {
        String filePathStr = "S:\\back-software\\ads-top55-analysis-20201217.csv";
//        String filePathStr = "/home/spark/dns-logs/sql/csv-result/ads-cdn-analysis_v2-20201203.csv";

        File file = new File(filePathStr);
        Path path = Paths.get(filePathStr);

        BasicFileAttributes bfa = Files.readAttributes(path, BasicFileAttributes.class);

        System.out.println("Creation Time      : " + bfa.creationTime());
        System.out.println("Last Access Time   : " + bfa.lastAccessTime());
        System.out.println("Last Modified Time : " + bfa.lastModifiedTime());
        System.out.println("Is Directory       : " + bfa.isDirectory());
        System.out.println("Is Other           : " + bfa.isOther());
        System.out.println("Is Regular File    : " + bfa.isRegularFile());
        System.out.println("Is Symbolic Link   : " + bfa.isSymbolicLink());
        System.out.println("Size               : " + bfa.size());
        System.out.println("Object Key               : " + bfa.fileKey());

        // linux 上生效1
        Object fileKey = bfa.fileKey();
        String s = fileKey.toString();
        String inode = s.substring(s.indexOf("ino=") + 4, s.indexOf(")"));
        System.out.println("Inode: " + inode);
        // linux 上生效2
        Long inode2 = (Long) Files.getAttribute(file.toPath(), "unix:ino");
        System.out.println(inode2 != null ? OptionalLong.of(inode2) : OptionalLong.empty());

    }
}
// stat log.txt
// https://www.ruanyifeng.com/blog/2011/12/inode.html
// https://my.oschina.net/u/2246410/blog/649257