package org.example.thread.volatile1;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class VolatileCountMain {

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask, "work");
        thread.start();

        sleep(1000);

        myTask.flag = false;
        log("flag = " + myTask.flag + ", count : " + myTask.count + " in main");
    }

    static class MyTask implements Runnable {
        boolean flag = true;
        long count;

        @Override
        public void run() {
            while (flag) {
                count++;
                if (count % 100_100_000 == 0) {
                    log("flag = " + flag + ", count : " + count + " in thread");
                }
            }
            log("flag = " + flag + ", count : " + count + " end");
        }
    }
}
