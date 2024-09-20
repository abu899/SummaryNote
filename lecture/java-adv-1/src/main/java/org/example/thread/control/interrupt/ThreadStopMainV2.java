package org.example.thread.control.interrupt;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class ThreadStopMainV2 {

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "Worker");
        thread.start();

        sleep(4000);
        log("Requesting stop...use Interrupt");
        thread.interrupt();
        log("Worker Thread Interrupt 상태 1: " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    log("Working...");
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                log("Worker Thread Interrupt 상태 2: " + Thread.currentThread().isInterrupted());
                log("interrupt message : " + e.getMessage());
                log("current status : " + Thread.currentThread().getState());
            }
            log("Resource released");
            log("Done");
        }
    }
}
