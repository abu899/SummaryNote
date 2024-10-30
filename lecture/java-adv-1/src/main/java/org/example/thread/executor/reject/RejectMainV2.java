package org.example.thread.executor.reject;

import org.example.thread.executor.RunnableTask;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectMainV2 {
    public static void main(String[] args) {
        ThreadPoolExecutor es
                = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy());

        es.submit(new RunnableTask("task1"));
        es.submit(new RunnableTask("task2"));
        es.submit(new RunnableTask("task3"));

        es.close();
    }
}
