package org.example.thread.control.join;

import org.example.util.ThreadUtils;

import static org.example.util.MyLogger.log;

public class JoinMainV2 {
    public static void main(String[] args) {
        log("start");
        SumTask task1 = new SumTask(1, 50);
        Thread thread1 = new Thread(task1, "thread-1");
        SumTask task2 = new SumTask(51, 100);
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        log("Wait for task1 and task2 to finish - sleep");
        ThreadUtils.sleep(3000);
        log("Wake up");

        log("task1.result = " + task1.result);
        log("task2.result = " + task2.result);

        int sumAll = task1.result + task2.result;
        log("sumAll = " + sumAll);
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
