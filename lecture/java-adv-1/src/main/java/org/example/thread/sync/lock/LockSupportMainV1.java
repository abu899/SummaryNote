package org.example.thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class LockSupportMainV1 {
    public static void main(String[] args) {
        ParkTest parkTest = new ParkTest();
        Thread thread1 = new Thread(parkTest, "park-thread-1");
        thread1.start();

        sleep(100);
        log("park-thread-1 state: " + thread1.getState());

        // 1. unpark
        log("main -> unpark park-thread-1");
        LockSupport.unpark(thread1);

        // 2. interrupt
        log("main -> interrupt park-thread-1");
        thread1.interrupt();
    }

    static class ParkTest implements Runnable {
        @Override
        public void run() {
            log("start park");
            LockSupport.park();
            log("end park, state: " + Thread.currentThread().getState());
            log("interrupted state: " + Thread.currentThread().isInterrupted());
        }
    }
}
