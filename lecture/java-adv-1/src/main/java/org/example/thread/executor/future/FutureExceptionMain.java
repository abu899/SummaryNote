package org.example.thread.executor.future;

import java.util.concurrent.*;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class FutureExceptionMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("Send task");
        Future<Integer> future = es.submit(new ExCallable());
        sleep(1000);

        try {
            log("try future get(), future state = " + future.state());
            Integer result = future.get();
            log("result = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            // 실행 중에 에러가 발생할 경우
            log("exception = " + e);
            Throwable cause = e.getCause();
            log("cause = " + cause);
        }

        es.close();
    }

    static class ExCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            log("Occur exception");
        throw new IllegalStateException("ex!");
        }
    }
}
