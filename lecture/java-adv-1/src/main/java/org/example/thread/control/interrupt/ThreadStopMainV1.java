package org.example.thread.control.interrupt;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class ThreadStopMainV1 {

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "Worker");
        thread.start();

        sleep(4000);
        log("Requesting stop...runFlag=false");
        myTask.runFlag = false;
    }

    static class MyTask implements Runnable {

        volatile boolean runFlag = true;

        @Override
        public void run() {
            while (runFlag) {
                log("Working...");
                sleep(3000);
            }
            log("Resource released");
            log("Done");
        }
    }
}
