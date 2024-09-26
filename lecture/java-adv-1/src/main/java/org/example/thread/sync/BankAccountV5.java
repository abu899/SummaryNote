
package org.example.thread.sync;

import org.example.util.ThreadUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.example.util.MyLogger.log;

public class BankAccountV5 implements BankAccount {
    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccountV5(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("transaction start: " + getClass().getSimpleName());

        if(!lock.tryLock()) {
            log("transaction failed, already withdrawn");
            return false;
        }

        lock.lock(); // ReentrantLock lock
        try {
            log("validation start");
            if (balance < amount) {
                log("validation failed amount: " + amount + " balance: " + balance);
                return false;
            }
            log("validation end, amount: " + amount + " balance: " + balance);
            ThreadUtils.sleep(1000); // simulate processing
            balance -= amount;
            log("withdraw end, amount: " + amount + " balance: " + balance);
        } finally {
            lock.unlock(); // ReentrantLock unlock
        }
        log("transaction end");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
