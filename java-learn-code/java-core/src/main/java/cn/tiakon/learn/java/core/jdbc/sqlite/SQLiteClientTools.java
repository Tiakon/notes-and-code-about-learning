package cn.tiakon.learn.java.core.jdbc.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteClientTools {

    private Connection connection = null;
    private Statement statement = null;

    public SQLiteClientTools() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            this.statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void main(String[] args) {
        SQLiteClientTools sqLiteClientTools = new SQLiteClientTools();
//        sqLiteClientTools.createTestTable();
        sqLiteClientTools.insertIntoTestTable();
        sqLiteClientTools.close();
    }

    private void createTestTable() {
        try {
            String sql = "CREATE TABLE COMPANY (ID INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE INT NOT NULL, ADDRESS CHAR(50), SALARY REAL);";
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void insertIntoTestTable() {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (11, 'Paul', 32, 'California', 20000.00 );";
            int executeUpdate = statement.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (12, 'Allen', 25, 'Texas', 15000.00 );";
            statement.addBatch(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (13, 'Teddy', 23, 'Norway', 20000.00 );";
            statement.addBatch(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (14, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
            statement.addBatch(sql);
            int[] executeBatch = statement.executeBatch();
            int totalExecuteUpdate = executeBatch.length + executeUpdate;
            System.out.println(">> 执行成功：" + totalExecuteUpdate + " 条");
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    private void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
