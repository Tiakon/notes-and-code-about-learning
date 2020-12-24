package cn.tiakon.learn.java.concurrency.imooc.wukong.sync;

/**
 * 可重入性度测试3:调用父类的方法。
 * last time   : 2020/9/26 17:35
 *
 * @author tiankai.me@gmail.com on 2020/9/26 17:35.
 */
public class Demo012_SynchronizedRecursion {

    public static void main(String[] args) throws InterruptedException {

        SonPerson sonPerson = new SonPerson();
        sonPerson.doSomething();

    }


}


class FatherPerson {
    public synchronized void doSomething() {
        System.out.println(">> I am is father...");
    }
}

class SonPerson extends FatherPerson {
    @Override
    public synchronized void doSomething() {
        System.out.println(">> I am is Son...");
        super.doSomething();
    }
}
