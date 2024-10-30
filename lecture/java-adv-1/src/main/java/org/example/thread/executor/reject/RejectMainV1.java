package org.example.thread.executor.reject;

import org.example.thread.executor.RunnableTask;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectMainV1 {
    public static void main(String[] args) {
        ThreadPoolExecutor es
                = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.AbortPolicy());

        es.submit(new RunnableTask("task1"));
        try {
            es.submit(new RunnableTask("task1"));
        } catch (RejectedExecutionException e) {
            System.out.println("task2 is rejected");
        }

        es.close();
    }
}
