package org.example.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class ExecutorBasicMain {
    public static void main(String[] args) {
        ExecutorService executorService
                = new ThreadPoolExecutor(2, 2, 0
                , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        log("== initial state ==");
        ExecutorUtils.printState(executorService);
        executorService.execute(new RunnableTask("taskA"));
        executorService.execute(new RunnableTask("taskB"));
        executorService.execute(new RunnableTask("taskC"));
        executorService.execute(new RunnableTask("taskD"));
        log("== working ... ==");
        ExecutorUtils.printState(executorService);

        sleep(3000);
        log("== after working ==");
        ExecutorUtils.printState(executorService);

        executorService.close();
        log("== shut down ==");
        ExecutorUtils.printState(executorService);
    }

}
