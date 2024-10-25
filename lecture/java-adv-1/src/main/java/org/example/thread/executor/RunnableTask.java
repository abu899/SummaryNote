package org.example.thread.executor;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class RunnableTask implements Runnable {
    private final String name;
    private int sleepMs = 1000;

    public RunnableTask(String name) {
        this.name = name;
    }

    public RunnableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public void run() {
        log(name + " start");
        sleep(sleepMs);
        log(name + " end");
    }
}
