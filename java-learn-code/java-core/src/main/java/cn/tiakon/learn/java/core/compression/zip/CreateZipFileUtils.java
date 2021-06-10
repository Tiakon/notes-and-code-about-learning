package cn.tiakon.learn.java.core.compression.zip;

import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.*;
import java.util.*;

public class CreateZipFileUtils {

    public static void createZipFileWithMultiEntry(String filePath, String password) {

        File files = new File(filePath);

        File[] listFiles = files.listFiles(name -> name.getName().endsWith(".log"));

        Map<String, List<File>> outFileMap = new HashMap<>();
        List<File> fileList;
        for (File file : Objects.requireNonNull(listFiles)) {
            if (file.isFile()) {
                String fileName = file.getName();
//                System.out.println(fileName);
//                System.out.println(file.getPath());
                // httpaccess_ltscsy.qq.com_20210121
                String filenameOut = fileName.substring(0, fileName.length() - 6).concat(".zip");

                if (outFileMap.containsKey(filenameOut)) {
                    fileList = outFileMap.get(filenameOut);
                    fileList.add(file);
                    outFileMap.put(filenameOut, fileList);
                } else {
                    fileList = new ArrayList<>();
                    fileList.add(file);
                    outFileMap.put(filenameOut, fileList);
                }

            }
        }


        for (Map.Entry<String, List<File>> listEntry : outFileMap.entrySet()) {
            String fileOutKey = listEntry.getKey();
            List<File> fileLists = listEntry.getValue();
            /*System.out.print(fileOutKey + " ");
            for (File file : fileLists) {
                System.out.print(file.getPath() + " ");
            }
            System.out.println();*/

            String fileOutPath = filePath.concat(File.separator).concat(fileOutKey);
            File fileOut = new File(fileOutPath);

            try {
                char[] pass = password.toCharArray();
                CompressionMethod compressionMethod = CompressionMethod.getCompressionMethodFromCode(8);
                EncryptionMethod zipStandard = EncryptionMethod.ZIP_STANDARD;
                AesKeyStrength aesKeyStrength = AesKeyStrength.getAesKeyStrengthFromRawCode(3);
                zipOutputStreamWithMultiEntry(fileOut, fileLists, pass, compressionMethod, true, zipStandard, aesKeyStrength);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void createZipFileWithSingleEntry(String filePath, String password) {

        File files = new File(filePath);

        File[] listFiles = files.listFiles(name -> name.getName().endsWith(".log"));

        Map<String, List<File>> outFileMap = new HashMap<>();
        List<File> fileList;
        for (File file : Objects.requireNonNull(listFiles)) {
            if (file.isFile()) {
                String fileName = file.getName();

                // httpaccess_ltscsy.qq.com_2021012106.log ->  httpaccess_ltscsy.qq.com_20210121.zip
                String filenameOut = fileName.substring(0, fileName.length() - 6).concat(".zip");

                if (outFileMap.containsKey(filenameOut)) {
                    fileList = outFileMap.get(filenameOut);
                    fileList.add(file);
                    outFileMap.put(filenameOut, fileList);
                } else {
                    fileList = new ArrayList<>();
                    fileList.add(file);
                    outFileMap.put(filenameOut, fileList);
                }

            }
        }

        for (Map.Entry<String, List<File>> listEntry : outFileMap.entrySet()) {
            String fileOutKey = listEntry.getKey();
            List<File> fileLists = listEntry.getValue();

            String fileOutPath = filePath.concat(File.separator).concat(fileOutKey);
            File fileOut = new File(fileOutPath);
            try {
                char[] pass = password.toCharArray();
                CompressionMethod compressionMethod = CompressionMethod.getCompressionMethodFromCode(8);
                EncryptionMethod zipStandard = EncryptionMethod.ZIP_STANDARD;
                AesKeyStrength aesKeyStrength = AesKeyStrength.getAesKeyStrengthFromRawCode(3);
                zipOutputStreamWithSingleEntry(fileOut, fileLists, pass, compressionMethod, true, zipStandard, aesKeyStrength);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void zipOutputStreamWithMultiEntry(File outputZipFile,
                                                     List<File> filesToAdd,
                                                     char[] password,
                                                     CompressionMethod compressionMethod,
                                                     boolean encrypt,
                                                     EncryptionMethod encryptionMethod,
                                                     AesKeyStrength aesKeyStrength) throws IOException {

        ZipParameters zipParameters = buildZipParameters(compressionMethod, encrypt, encryptionMethod, aesKeyStrength);
        byte[] buff = new byte[8192];
        int readLen;

        try (ZipOutputStream zos = initializeZipOutputStream(outputZipFile, encrypt, password)) {
            for (File fileToAdd : filesToAdd) {

                // Entry size has to be set if you want to add entries of STORE compression method (no compression)
                // This is not required for deflate compression
                if (zipParameters.getCompressionMethod() == CompressionMethod.STORE) {
                    zipParameters.setEntrySize(fileToAdd.length());
                }

                zipParameters.setFileNameInZip(fileToAdd.getName());
                zos.putNextEntry(zipParameters);

                try (InputStream inputStream = new FileInputStream(fileToAdd)) {
                    while ((readLen = inputStream.read(buff)) != -1) {
                        zos.write(buff, 0, readLen);
                    }
                }
                zos.closeEntry();
            }
        }
    }

    private static void zipOutputStreamWithSingleEntry(File outputZipFile,
                                                      List<File> filesToAdd,
                                                      char[] password,
                                                      CompressionMethod compressionMethod,
                                                      boolean encrypt,
                                                      EncryptionMethod encryptionMethod,
                                                      AesKeyStrength aesKeyStrength) throws IOException {

        ZipParameters zipParameters = buildZipParameters(compressionMethod, encrypt, encryptionMethod, aesKeyStrength);
        byte[] buff = new byte[8192];
        int readLen;

        try (ZipOutputStream zos = initializeZipOutputStream(outputZipFile, encrypt, password)) {
            // Entry size has to be set if you want to add entries of STORE compression method (no compression)
            // This is not required for deflate compression
            if (zipParameters.getCompressionMethod() == CompressionMethod.STORE) {
                zipParameters.setEntrySize(1);
            }
            // httpaccess_ltscsy.qq.com_20210121.zip -> httpaccess_ltscsy.qq.com_20210121.log
            zipParameters.setFileNameInZip(outputZipFile.getName().substring(0, outputZipFile.getName().length() - 4).concat(".log"));
            zos.putNextEntry(zipParameters);
            for (File fileToAdd : filesToAdd) {
                try (InputStream inputStream = new FileInputStream(fileToAdd)) {
                    while ((readLen = inputStream.read(buff)) != -1) {
                        zos.write(buff, 0, readLen);
                    }
                }
            }
            zos.closeEntry();
        }
    }

    private static ZipOutputStream initializeZipOutputStream(File outputZipFile, boolean encrypt, char[] password) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputZipFile);
        if (encrypt) {
            return new ZipOutputStream(fos, password);
        }
        return new ZipOutputStream(fos);
    }

    private static ZipParameters buildZipParameters(CompressionMethod compressionMethod, boolean encrypt, EncryptionMethod encryptionMethod, AesKeyStrength aesKeyStrength) {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(compressionMethod);
        zipParameters.setEncryptionMethod(encryptionMethod);
        zipParameters.setAesKeyStrength(aesKeyStrength);
        zipParameters.setEncryptFiles(encrypt);
        return zipParameters;
    }

}
