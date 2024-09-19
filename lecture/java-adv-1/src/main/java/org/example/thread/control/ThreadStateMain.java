package org.example.thread.control;

import static org.example.util.MyLogger.log;

public class ThreadStateMain {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyRunnable(), "myThread");
        log("myThread.state1 = " + thread.getState()); // NEW
        log("myThread.start()");
        thread.start();
        Thread.sleep(1000);
        log("myThread.state3 = " + thread.getState()); // TIME_WAITING
        Thread.sleep(3000);
        log("myThread.state5 = " + thread.getState()); // TERMINATED
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                log("start");
                log("myThread.state2 = " + Thread.currentThread().getState()); // RUNNABLE
                log("sleep() start");
                Thread.sleep(2000);
                log("sleep() end");
                log("myThread.state4 = " + Thread.currentThread().getState()); // RUNNABLE
                log("end");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
