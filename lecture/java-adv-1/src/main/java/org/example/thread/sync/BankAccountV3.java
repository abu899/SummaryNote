package org.example.thread.sync;

import org.example.util.ThreadUtils;

import static org.example.util.MyLogger.log;

public class BankAccountV3 implements BankAccount {
    private int balance;

    public BankAccountV3(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("transaction start: " + getClass().getSimpleName());

        synchronized (this) {
            log("validation start");
            if (balance < amount) {
                log("validation failed amount: " + amount + " balance: " + balance);
                return false;
            }
            log("validation end, amount: " + amount + " balance: " + balance);
            ThreadUtils.sleep(1000); // simulate processing
            balance -= amount;
            log("withdraw end, amount: " + amount + " balance: " + balance);
        }

        log("transaction end");
        return true;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
