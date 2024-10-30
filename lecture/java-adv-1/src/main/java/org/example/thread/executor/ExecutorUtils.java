package org.example.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static org.example.util.MyLogger.log;

public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queued = poolExecutor.getQueue().size();
            long completedTaskCount = poolExecutor.getCompletedTaskCount();
            log(String.format("[pool=%d, active=%d, queued=%d, completed=%d]", pool, active, queued, completedTaskCount));
        } else {
            log(executorService);
        }
    }

    public static void printState(ExecutorService executorService, String taskName) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize();
            int active = poolExecutor.getActiveCount();
            int queued = poolExecutor.getQueue().size();
            long completedTaskCount = poolExecutor.getCompletedTaskCount();
            log(String.format("%s -> [pool=%d, active=%d, queued=%d, completed=%d]", taskName, pool, active, queued, completedTaskCount));
        } else {
            log(executorService);
        }
    }
}
