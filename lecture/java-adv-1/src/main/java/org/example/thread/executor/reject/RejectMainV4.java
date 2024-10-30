package org.example.thread.executor.reject;

import org.example.thread.executor.RunnableTask;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.util.MyLogger.log;

public class RejectMainV4 {
    public static void main(String[] args) {
        ThreadPoolExecutor es
                = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new MyRejectedExecutionHandler());

        es.submit(new RunnableTask("task1"));
        es.submit(new RunnableTask("task2"));
        es.submit(new RunnableTask("task3"));
        es.submit(new RunnableTask("task4"));

        es.close();
    }

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler {
        static AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            int i = counter.incrementAndGet();
            log("rejected count sum : " + i);
        }
    }
}
