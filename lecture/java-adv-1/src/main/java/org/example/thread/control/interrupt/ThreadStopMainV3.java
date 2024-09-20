package org.example.thread.control.interrupt;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class ThreadStopMainV3 {

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
            while (!Thread.currentThread().isInterrupted()) { // isInterrupt()는 인터러브 상태를 바꾸지 않음
                log("Working...");
            }
            log("Worker Thread Interrupt 상태 2: " + Thread.currentThread().isInterrupted()); // 인터럽트 상태 유지
            try {
                log("Resource released");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log("Resource released Failed!!!"); // 인터럽트 상태가 유지되면서 예외 발생
                log("Worker Thread Interrupt 상태 3: " + Thread.currentThread().isInterrupted());
            }
            log("Done");
        }
    }
}
