package org.example.thread.control.join;

import org.example.util.ThreadUtils;

import static org.example.util.MyLogger.log;

public class JoinMainV4 {
    public static void main(String[] args) throws InterruptedException {
        log("start");
        SumTask task1 = new SumTask(1, 50);
        Thread thread1 = new Thread(task1, "thread-1");
        thread1.start();

        log("Wait for task1 to finish - join(1000)");
        thread1.join(1000);
        log("task1 finished - join");

        log("task1.result = " + task1.result);
        log("end");
    }

    static class SumTask implements Runnable {

        int startValue;
        int endValue;
        int result;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("Sum start");
            ThreadUtils.sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; ++i) {
                sum += i;
            }
            result = sum;
            log("Sum end");
        }
    }
}
