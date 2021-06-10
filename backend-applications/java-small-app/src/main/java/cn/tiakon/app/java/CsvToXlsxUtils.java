package cn.tiakon.app.java;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

/**
 *
 * https://stackoverflow.com/questions/18077264/convert-csv-to-xls-xlsx-using-apache-poi
 * @author Administrator
 */
public class CsvToXlsxUtils {

    public static void main(String[] args) {

        String csvFileAddress = "C:\\Users\\Administrator\\Desktop\\河南移动日志分析\\client-ip-domain-20200818.csv"; //csv file address
        String xlsxFileAddress = "C:\\Users\\Administrator\\Desktop\\河南移动日志分析\\client-ip-domain-20200818.xlsx"; //xlsx file address

//        long startTime1 = System.currentTimeMillis();
//        CsvToXlsxUtils.csvToXLSX(xlsxFileAddress,csvFileAddress);
//        System.out.println(System.currentTimeMillis() - startTime1);
        try {
            long startTime2 = System.currentTimeMillis();
            CsvToXlsxUtils.convertCsvToXlsx(xlsxFileAddress,csvFileAddress);
            System.out.println(System.currentTimeMillis() - startTime2);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void csvToXLSX(String xlsxFileAddress, String csvFileAddress) {
        try {

            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine = null;
            int RowNum = 0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow = sheet.createRow(RowNum);
                for (int i = 0; i < str.length; i++) {
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "Exception in try");
        }
    }

    public static void convertCsvToXlsx(String xlsLocation, String csvLocation) throws Exception {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("Sheet");
        AtomicReference<Integer> row = new AtomicReference<>(0);
        Files.readAllLines(Paths.get(csvLocation)).forEach(line -> {
            Row currentRow = sheet.createRow(row.getAndSet(row.get() + 1));
            String[] nextLine = line.split(",");
            Stream.iterate(0, i -> i + 1).limit(nextLine.length).forEach(i -> {
                currentRow.createCell(i).setCellValue(nextLine[i]);
            });
        });
        FileOutputStream fos = new FileOutputStream(new File(xlsLocation));
        workbook.write(fos);
        fos.flush();
    }

}
