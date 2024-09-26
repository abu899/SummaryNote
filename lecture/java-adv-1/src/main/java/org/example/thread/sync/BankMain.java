package org.example.thread.sync;

import org.example.util.ThreadUtils;

import static org.example.util.MyLogger.log;

public class BankMain {
    public static void main(String[] args) throws InterruptedException {
//        BankAccount bankAccount = new BankAccountV1(1000);
//        BankAccount bankAccount = new BankAccountV2(1000);
//        BankAccount bankAccount = new BankAccountV3(1000);
//        BankAccount bankAccount = new BankAccountV4(1000);
        BankAccount bankAccount = new BankAccountV5(1000);


        WithdrawTask withdrawTask = new WithdrawTask(bankAccount, 800);
        Thread thread1 = new Thread(withdrawTask, "t1");
        Thread thread2 = new Thread(withdrawTask, "t2");

        thread1.start();
        thread2.start();

        ThreadUtils.sleep(500);
        log("t1 state: " + thread1.getState());
        log("t2 state: " + thread2.getState());

        thread1.join();
        thread2.join();

        log("balance: " + bankAccount.getBalance());
    }
}
