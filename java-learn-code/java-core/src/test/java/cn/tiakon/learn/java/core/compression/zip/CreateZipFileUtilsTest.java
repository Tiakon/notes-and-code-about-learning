package cn.tiakon.learn.java.core.compression.zip;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateZipFileUtilsTest {

    String filePath = "R:\\user\\temp\\test";
    String password = "Vso8VgKQ_I5";

    @Before
    public void init() {

    }

    @After
    public void end() {


    }

    @Test
    public void createZipFileWithMultiEntryTest() {
        CreateZipFileUtils.createZipFileWithMultiEntry(filePath, password);
    }

    @Test
    public void createZipFileWithSingleEntryTest() {
        CreateZipFileUtils.createZipFileWithSingleEntry(filePath, password);
    }

}
