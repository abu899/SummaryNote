package org.example.thread.executor.poolsize;

import org.example.thread.executor.RunnableTask;

import java.util.concurrent.*;

import static org.example.thread.executor.ExecutorUtils.printState;
import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class PoolSizeMainV1 {
    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);

        ExecutorService es =
                new ThreadPoolExecutor(2, 4, 3000, TimeUnit.MILLISECONDS, workQueue);
        printState(es);

        es.execute(new RunnableTask("task1"));
        printState(es, "task1");

        es.execute(new RunnableTask("task2"));
        printState(es, "task2");

        es.execute(new RunnableTask("task3"));
        printState(es, "task3");

        es.execute(new RunnableTask("task4"));
        printState(es, "task4");

        // BlockingQueue가 가득 찬 이후
        es.execute(new RunnableTask("task5"));
        printState(es, "task5");

        es.execute(new RunnableTask("task6"));
        printState(es, "task6");

        // pool 도 가득 찬 경우
        try {
            es.execute(new RunnableTask("task7"));
            printState(es, "task7");
        } catch (RejectedExecutionException e) {
            log("task7 is rejected");
        }

        sleep(3000);
        log("== work done ==");
        printState(es);

        sleep(3000);
        log("== exceed maximum idle time ==");
        printState(es);

        es.close();
        log("== shutdown ==");
        printState(es);
    }
}
