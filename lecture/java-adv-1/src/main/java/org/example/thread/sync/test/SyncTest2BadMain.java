package org.example.thread.sync.test;

import org.example.util.MyLogger;

import static org.example.util.MyLogger.log;

/**
 * localValue 지역 변수에 동시성 문제가 발생하는지 하지 않는지 생각하고 결과 예측
 * 예측: 1000, 로컬 변수는 각 스레드에서 독립적으로 관리
 */
public class SyncTest2BadMain {
    public static void main(String[] args) throws InterruptedException {
        MyCounter myCounter = new MyCounter();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                myCounter.count();
            }
        };
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        thread1.start();
        thread2.start();
    }
    static class MyCounter {
        public void count() {
            int localValue = 0;
            for (int i = 0; i < 1000; i++) {
                localValue = localValue + 1;
            }
            log("result: " + localValue);
        }
    }
}
