package cn.tiakon.learn.java.ppo.part02;

/**
 * 代理模式
 * last time   : 2020/7/14 22:52
 *
 * @author tiankai.me@gmail.com on 2020/7/14 22:52.
 */
public class DemoPage17 {
    public static void main(String[] args) {
        // 使用代理。
        IDBQuery dbQuery = new DBQueryProxy();
        // 真正使用时，才创建对象。
//        System.out.println(dbQuery.request());
    }
}

interface IDBQuery {
    String request();
}

class DBQuery implements IDBQuery {

    public DBQuery() {
        try {
            // 模拟数据库连接、查询等耗时操作。
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String request() {
        return "request string";
    }
}

class DBQueryProxy implements IDBQuery {

    private DBQuery real = null;

    @Override
    public String request() {
        // 真正使用时才创建对象，创建过程可能很慢。
        if (real == null) {
            // 在多线程环境下，这里返回一个虚假类，类似Future模式。
            real = new DBQuery();
        }
        return real.request();
    }
}

