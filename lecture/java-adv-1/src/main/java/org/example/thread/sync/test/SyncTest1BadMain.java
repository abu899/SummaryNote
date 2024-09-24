package org.example.thread.sync.test;

/**
 * 결과가 20000이 되어야함
 * 문제점을 찾아 해결
 */
public class SyncTest1BadMain {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.increment();
                }
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("result: " + counter.getCount());
    }
    static class Counter {
        private int count = 0;
        public synchronized void increment() {
            count = count + 1;
        }
        public synchronized int getCount() {
            return count;
        }
    }
}
