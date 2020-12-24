package cn.tiakon.learn.java.multithread.chapter08_deadlock;

import java.util.Random;
/**
 * 500个账户中，20个人，每人同时转账 100000 次
 *
 *
 * last time   : 2020/11/5 8:24
 *
 * @author tiankai.me@gmail.com on 2020/11/5 8:24.
 */
public class Demo03_MultiTransferMoney {

    private static final int NUM_ACCOUNT = 500;
    private static final int BALANCE_ACCOUNT = 1000;
    private static final int COUNT_TRANSFER = 1000000;
    private static final int COUNT_PEOPLE = 20;

    public static void main(String[] args) {
        Random random = new Random();
        Account[] accounts = new Account[NUM_ACCOUNT];
        for (int i = 0; i < accounts.length ; i++) {
            accounts[i] = new Account(BALANCE_ACCOUNT);
        }

        class TransferThread implements Runnable {
            @Override
            public void run() {
                for (int i = 0; i < COUNT_TRANSFER; i++) {
                    int fromAccount = random.nextInt(NUM_ACCOUNT);
                    int toAccount = random.nextInt(NUM_ACCOUNT);
                    int amount = random.nextInt(BALANCE_ACCOUNT);
                    if (fromAccount != toAccount) {
                        TransferMoney.transferMoney(accounts[fromAccount], accounts[toAccount], amount);
                    } else {
                        System.out.println("不能给自己转账!");
                    }
                }
                System.out.println("转账结束.");
            }
        }

        TransferThread transferThread = new TransferThread();

        for (int i = 0; i < COUNT_PEOPLE; i++) {
            new Thread(transferThread).start();
        }

    }

}
