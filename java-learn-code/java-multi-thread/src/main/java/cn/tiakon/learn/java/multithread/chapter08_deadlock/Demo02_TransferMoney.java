package cn.tiakon.learn.java.multithread.chapter08_deadlock;

/**
 * 两人相互转账，当一人持有锁时发生等待就会造成死锁。
 * last time   : 2020/11/4 8:14
 *
 * @author tiankai.me@gmail.com on 2020/11/4 8:14.
 */
public class Demo02_TransferMoney {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account("a", 500);
        Account b = new Account("b", 500);
        TransferMoney m1 = new TransferMoney("a", a, b);
        TransferMoney m2 = new TransferMoney("b", a, b);
        Thread t1 = new Thread(m1);
        Thread t2 = new Thread(m2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a 余额：" + m1.a.balance);
        System.out.println("b 余额：" + m1.b.balance);
    }

}

class TransferMoney implements Runnable {

    private String flag;
    Account a;
    Account b;

    public TransferMoney(String flag, Account a, Account b) {
        this.flag = flag;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        if ("a".equals(flag)) {
            System.out.println("用户：" + flag + " 进入操作");
//            transferMoney(a, b, 200);
            transferMoneyByOrder(a, b, 200);
        }
        if ("b".equals(flag)) {
            System.out.println("用户：" + flag + " 进入操作");
//            transferMoney(b, a, 200);
            transferMoneyByOrder(a, b, -200);
        }
    }

    public static void transferMoney(Account from, Account to, int amount) {
        synchronized (from) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (to) {
                if (from.balance > amount) {
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println("转账成功,余额为:" + from.balance);
                } else {
                    System.out.println("转账失败,余额不足");
                }
            }
        }
    }

    // 控制获取锁的顺序，保障获取锁的顺序是一致的。
    public static void transferMoneyByOrder(Account from, Account to, int amount) {
        synchronized (from) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (to) {
                if (amount > 0 && from.balance > amount) {
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println(from.name + " 转账成功,余额为:" + from.balance);
                } else if (amount < 0 && from.balance > amount * (-1)) {
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println(to.name + " 转账成功,余额为:" + to.balance);
                } else {
                    System.out.println("转账失败,余额不足");
                }
            }
        }
    }
}

class Account {
    String name;
    int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
}