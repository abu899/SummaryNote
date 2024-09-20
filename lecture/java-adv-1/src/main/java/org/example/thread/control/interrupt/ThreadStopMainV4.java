package org.example.thread.control.interrupt;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class ThreadStopMainV4 {

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "Worker");
        thread.start();

        sleep(100);
        log("Requesting stop...use Interrupt");
        thread.interrupt();
        log("Worker Thread Interrupt 상태 1: " + thread.isInterrupted());
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) { // interrupted()는 인터럽트의 상태를 확인하고 이를 바꾼다
                log("Working...");
            }
            log("Worker Thread Interrupt 상태 2: " + Thread.currentThread().isInterrupted());
            try {
                log("Resource released");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log("Resource released Failed!!!");
                log("Worker Thread Interrupt 상태 3: " + Thread.currentThread().isInterrupted());
            }
            log("Done");
        }
    }
}
