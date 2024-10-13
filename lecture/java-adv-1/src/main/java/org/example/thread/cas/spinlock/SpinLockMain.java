package org.example.thread.cas.spinlock;

import static org.example.util.MyLogger.log;

public class SpinLockMain {
    public static void main(String[] args) {
//        SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                spinLock.lock();
                try {
                    log("비지니스 로직 실행");
//                    sleep(1); // CAS의 단점, 오래 걸리는 로직에는 스핀락을 사용하면 안된다
                } finally {
                    spinLock.unlock();
                }
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();
    }
}
