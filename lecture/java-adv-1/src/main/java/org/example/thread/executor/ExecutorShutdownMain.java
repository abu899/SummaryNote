package org.example.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.example.thread.executor.ExecutorUtils.printState;
import static org.example.util.MyLogger.log;

public class ExecutorShutdownMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000));
        printState(es);
        log("== start shutdown ==");
        shutdownAndAwaitTermination(es);
        log("== end shutdown ==");
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // non-blocking, 새로운 작업 받지 않음, 처리 중인 작업은 처리, 대기 중인 작업은 취소
        try {
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                // 10초가 지나도 작업이 완료되지 않으면 강제 종료
                log("Service not terminated. Try force shutdown");
                es.shutdownNow();

                // 작업이 취소될 떄 까지 대기
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("Service did not terminate");
                }
            }
        } catch (InterruptedException e) {
            // awaitTermination()을 통해 대기 중인 스레드에 해당 예외가 발생
            es.shutdownNow();
        }
    }
}
