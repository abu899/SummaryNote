package org.example.thread.executor.future;

import java.util.concurrent.*;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class FutureCancelMain {
//    private static boolean mayInterruptIfRunning = true;
    private static boolean mayInterruptIfRunning = false;

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        log("Future state = {}" + future.state());

        sleep(3000);

        log("Call future.cancel(" + mayInterruptIfRunning + ")");
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        log("Cancel(" + mayInterruptIfRunning + ") result = " + cancelResult);

        try {
            log("Future result = " + future.get());
        } catch (CancellationException e) {
            log("Future is already cancelled");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        es.close();
    }

    static class MyTask implements Callable<String> {
        @Override
        public String call() {
            try {
                for (int i = 0; i < 10; i++) {
                    log("working : " + i);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log("Interrupted");
                return "Interrupted";
            }

            return "Completed";
        }
    }

}
