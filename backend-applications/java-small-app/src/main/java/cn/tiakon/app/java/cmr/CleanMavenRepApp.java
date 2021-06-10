package cn.tiakon.app.java.cmr;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

/**
 * 1. 删除 maven仓库中后缀为 .lastUpdated 的文件。
 * 2. 删除 maven仓库中的空目录。
 *
 * @author Tiakon 2020/2/17 22:51
 */
@Slf4j
public class CleanMavenRepApp {

    private static final Boolean IS_DELETE_EMPTY_DIR = false;
    private static final Boolean IS_DELETE_LAST_UPDATED_FILE = false;

    public static void main(String[] args) {

        String mavenRepoRoot = "R:\\maven-repository\\default-repo";
//        String mavenRepoRoot = "R:\\maven-repository\\test1";

        File rootFile = new File(mavenRepoRoot);

        if (!rootFile.isDirectory()) {
            throw new RuntimeException("输入路径不是目录！");
        }
        Long fileSize = 0L;

        Long totalFileSize = CleanMavenRepApp.findTargetFile(rootFile, fileSize);

        log.info(">> 已清除文件的总字节:{}", totalFileSize);

    }

    private static Long findTargetFile(File targetFile, Long targetFileTempSize) {

        Long targetFileTempSizeTemp = targetFileTempSize;

        for (File file : Objects.requireNonNull(targetFile.listFiles())) {
            if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length != 0) {
                targetFileTempSizeTemp += CleanMavenRepApp.findTargetFile(file, targetFileTempSize);
            } else if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length == 0) {
                String path = file.getPath();

                if (IS_DELETE_EMPTY_DIR) {
                    boolean delete = file.delete();
                    System.out.printf("%s -> 删除状态:%s \r\n", path, String.valueOf(delete));
                } else {
                    System.out.printf("%s \r\n", path);
                }
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".lastUpdated")) {
                    String path = file.getPath();
                    targetFileTempSizeTemp += file.length();
                    if (IS_DELETE_LAST_UPDATED_FILE) {
                        boolean delete = file.delete();
                        System.out.printf("%s -> 删除状态:%s \r\n", path, String.valueOf(delete));
                    } else {
                        System.out.printf("%s \r\n", path);
                    }
                }
            }
        }
        return targetFileTempSizeTemp;
    }

}
