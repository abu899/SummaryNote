package org.example.thread.executor.future;

import java.util.concurrent.*;

import static org.example.util.MyLogger.log;

public class SumTaskMainV2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log("start");
        ExecutorService es = Executors.newFixedThreadPool(2);
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        Future<Integer> future1 = es.submit(task1);
        Future<Integer> future2 = es.submit(task2);

        Integer result1 = future1.get();
        Integer result2 = future2.get();

        log("task1.result = " + result1);
        log("task2.result = " + result2);

        int sumAll = result1 + result2;
        log("sumAll = " + sumAll);

        es.close();
        log("end");
    }

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws Exception {
            log("Sum start");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = startValue; i <= endValue; ++i) {
                sum += i;
            }
            return sum;
        }
    }
}
