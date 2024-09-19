package org.example.thread.control;

import org.example.thread.start.HelloRunnable;

import static org.example.util.MyLogger.log;

public class ThreadInfoMain {
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        log("thread = " + thread);
        log("thread.threadId() = " + thread.threadId());
        log("thread.getName() = " + thread.getName());
        log("thread.getPriority() = " + thread.getPriority());
        log("thread.getState() = " + thread.getState());
        log("thread.getThreadGroup() = " + thread.getThreadGroup());

        Thread myThread = new Thread(new HelloRunnable(), "myThread");
        log("myThread = " + myThread);
        log("myThread.threadId() = " + myThread.threadId());
        log("myThread.getName() = " + myThread.getName());
        log("myThread.getPriority() = " + myThread.getPriority());
        log("myThread.getState() = " + myThread.getState());
        log("myThread.getThreadGroup() = " + myThread.getThreadGroup());
    }
}
