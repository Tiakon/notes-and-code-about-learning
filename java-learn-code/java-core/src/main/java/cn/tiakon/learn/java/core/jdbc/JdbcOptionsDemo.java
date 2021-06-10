package cn.tiakon.learn.java.core.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static cn.tiakon.learn.java.core.jdbc.JdbcTools.printResultSet;

// jdbc驱动源码分析 https://blog.csdn.net/QH_JAVA/article/details/50390038
public class JdbcOptionsDemo {

    private String jdbcUrl = "jdbc:mysql://localhost:3306/db_test?useSSL=false";
    private String user = "root";
    private String password = "123456";

    // 显式声明第三方数据库的API
    @Test
    public void getConnection01Test() {
        try {
            //1.提供java.sql.Driver接口实现类的对象
            Driver driver = null;
            driver = new com.mysql.jdbc.Driver();

            //2.提供url，指明具体操作的数据
            String url = jdbcUrl;

            //3.提供Properties的对象，指明用户名和密码
            Properties info = new Properties();
            info.setProperty("user", user);
            info.setProperty("password", password);

            //4.调用driver的connect()，获取连接
            Connection conn = driver.connect(url, info);
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 说明：相较于方式一，这里使用反射实例化Driver，不在代码中体现第三方数据库的API。体现了面向接口编程思想。
    @Test
    public void getConnection02Test() {
        try {
            //1.实例化Driver
            String className = "com.mysql.jdbc.Driver";
            Class clazz = Class.forName(className);
            Driver driver = (Driver) clazz.newInstance();

            //2.提供url，指明具体操作的数据
            String url = jdbcUrl;

            //3.提供Properties的对象，指明用户名和密码
            Properties info = new Properties();
            info.setProperty("user", user);
            info.setProperty("password", password);

            //4.调用driver的connect()，获取连接
            Connection conn = driver.connect(url, info);
            System.out.println(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 说明：使用DriverManager实现数据库的连接。体会获取连接必要的4个基本要素。
    @Test
    public void getConnection03Test() {
        try {
            //1.数据库连接的4个基本要素：
            String url = jdbcUrl;
            String driverName = "com.mysql.jdbc.Driver";

            //2.实例化Driver
            Class clazz = Class.forName(driverName);
            Driver driver = (Driver) clazz.newInstance();
            //3.注册驱动
            DriverManager.registerDriver(driver);
            //4.获取连接
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 说明：不必显式的注册驱动了。因为在DriverManager的源码中已经存在静态代码块，实现了驱动的注册。
    @Test
    public void getConnection04Test() {
        try {
            //1.数据库连接的4个基本要素：
            String driverName = "com.mysql.jdbc.Driver";
            //2.加载驱动 （①实例化Driver ②注册驱动）
            Class.forName(driverName);
            //3.获取连接
            Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 最终推荐
    // 说明：使用配置文件的方式保存配置信息，在代码中加载配置文件
    //**使用配置文件的好处：**
    //①实现了代码和数据的分离，如果需要修改配置信息，直接在配置文件中修改，不需要深入代码
    //②如果修改了配置信息，省去重新编译的过程。
    @Test
    public void getConnection05Test() throws IOException, ClassNotFoundException, SQLException {
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
        // https://blog.csdn.net/QH_JAVA/article/details/50390038
        // https://objcoding.com/2017/07/03/JDBC/
        // https://www.cnblogs.com/ZhangZiSheng001/p/11917307.html
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);

        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery("show tables");

        printResultSet(resultSet);

    }

    // https://github.com/alibaba/druid
    @Test
    public void DruidDataSourceTest() throws Exception {
        Properties pro = new Properties();
        pro.load(JdbcOptionsDemo.class.getClassLoader().getResourceAsStream("druid.properties"));
        DataSource ds = DruidDataSourceFactory.createDataSource(pro);
        Connection conn = ds.getConnection();
        System.out.println(conn);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("show databases");
        printResultSet(resultSet);
    }

    // https://github.com/brettwooldridge/HikariCP
    @Test
    public void HikariDataSourceTest() throws IOException {
        Properties pro = new Properties();
        pro.load(JdbcOptionsDemo.class.getClassLoader().getResourceAsStream("hikari.properties"));
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(pro.getProperty("jdbcUrl"));
        config.setUsername(pro.getProperty("username"));
        config.setPassword(pro.getProperty("password"));
        config.setDataSourceClassName(pro.getProperty("dataSourceClassName"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);
        try (Connection conn = ds.getConnection()) {
            System.out.println(conn.getMetaData().getDriverName());
            System.out.println(conn.getMetaData().getDatabaseProductName());
            System.out.println(conn.getMetaData().getDatabaseProductName());
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("show databases");
            printResultSet(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

