package cn.tiakon.learn.java.core.jdbc;

import java.io.InputStream;
import java.sql.*;
import java.util.*;


public class JdbcTools {

    private Connection connection;

    public JdbcTools() {
        try {
            //1.加载配置文件
            InputStream is = JdbcOptionsDemo.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);

            //2.读取配置信息
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            if (driverClass.length() != 0) {
                //3.加载驱动
                Class.forName(driverClass);
            }

            //4.获取连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void printResultSet(ResultSet resultSet) {
        List<Map<String, String>> resultList = convertList(resultSet);
        for (Map<String, String> maps : resultList) {
            Set<Map.Entry<String, String>> entrySet = maps.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }

    private static List<Map<String, String>> convertList(ResultSet rs) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            //获取键名
            ResultSetMetaData md = rs.getMetaData();

            //获取行的数量
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                //声明Map
                Map<String, String> rowData = new HashMap<String, String>();
                for (int i = 1; i <= columnCount; i++) {
                    //获取键名及值
                    rowData.put(md.getColumnName(i), rs.getObject(i).toString());
                }
                list.add(rowData);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return list;
    }

    public static void printResultSetV2(ResultSet resultSet) throws SQLException {
        // 获取结果集的元数据
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int rsmdColumnCount = rsmd.getColumnCount();

        StringBuffer columnsBuffer = new StringBuffer();
        StringBuffer valuesBuffer = new StringBuffer();

        boolean isAdded = false;

        while (resultSet.next()) {
            for (int j = 0; j < rsmdColumnCount; j++) {
                String columnName = rsmd.getColumnName(j + 1);
                String columnValue = String.valueOf(resultSet.getObject(columnName));
                int max = Math.max(columnName.length(), String.valueOf(columnValue).length());

                if (!isAdded) {
                    if (columnName.length() != max) {
                        columnName = columnName.concat(getTabStr(max - columnName.length()));
                    }
                    columnsBuffer.append(columnName).append("\t");
                }

                if (columnValue.length() != max) {
                    columnValue = columnValue.concat(getTabStr(max - columnValue.length()));
                }

                valuesBuffer.append(columnValue).append("\t");
            }
            if (!isAdded) {
                columnsBuffer.append("\r\n");
                isAdded = true;
            }
            valuesBuffer.append("\r\n");
        }

        System.out.print(columnsBuffer.toString());
        System.out.println(valuesBuffer.toString());
    }

    private static String getTabStr(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(" ");
        }
        return result.toString();
    }

}
