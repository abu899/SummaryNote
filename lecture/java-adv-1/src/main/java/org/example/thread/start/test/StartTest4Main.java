package org.example.thread.start.test;

import static org.example.util.MyLogger.log;

public class StartTest4Main {
    public static void main(String[] args) {
        Thread thread = new Thread(new Worker("A", 1000), "Worker-A");
        Thread thread2 = new Thread(new Worker("B", 500), "Worker-B");
        thread.start();
        thread2.start();
    }

    static class Worker implements Runnable {
        private String content;
        private int interval;

        public Worker(String content, int interval) {
            this.content = content;
            this.interval = interval;
        }

        @Override
        public void run() {
            while (true) {
                log(content);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
