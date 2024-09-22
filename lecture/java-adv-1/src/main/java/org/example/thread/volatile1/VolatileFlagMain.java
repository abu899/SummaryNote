package org.example.thread.volatile1;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class VolatileFlagMain {
    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "work");
        log("flag = " + myTask.flag);
        thread.start();

        sleep(1000);
        log("set flag to false");
        myTask.flag = false;
        log("flag = " + myTask.flag);
        log("main end");
    }

    static class MyTask implements Runnable {
        boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                log("task start");
                while(flag) {
                    // do something
                }
                log("task end");
            }
        }
    }
}
