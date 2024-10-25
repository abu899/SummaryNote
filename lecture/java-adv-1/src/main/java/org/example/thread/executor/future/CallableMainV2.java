package org.example.thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class CallableMainV2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        log("call submit");
        Future<Integer> future = executorService.submit(new MyCallable());
        log("right after submit = " + future);
        log("future.get() [blocking] Call method start");
        Integer result = future.get();
        log("future.get() [blocking] Call method complete");
        log("result = " + result);
        log("future = " + future);
        executorService.close();
    }

    static class MyCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            log("Start Callable");
            sleep(2000);
            int value = new Random().nextInt(10);
            log("create value = " + value);
            log("End Callable");
            return value;
        }
    }
}
