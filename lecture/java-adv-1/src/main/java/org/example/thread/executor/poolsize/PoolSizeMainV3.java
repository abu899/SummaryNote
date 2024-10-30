package org.example.thread.executor.poolsize;

import org.example.thread.executor.RunnableTask;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.example.thread.executor.ExecutorUtils.printState;
import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class PoolSizeMainV3 {
    public static void main(String[] args) {
//        ExecutorService es = Executors.newCachedThreadPool();
        ThreadPoolExecutor es
                = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        log("make pool");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }

        sleep(3000);
        log("== work done ==");
        printState(es);

        sleep(3000);
        log("== exceed maximum idle time ==");
        printState(es);

        es.close();
        log("== shutdown ==");
    }
}
