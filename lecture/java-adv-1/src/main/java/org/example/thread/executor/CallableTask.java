package org.example.thread.executor;

import java.util.concurrent.Callable;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class CallableTask implements Callable<Integer> {

    private final String name;
    private int sllepMs = 1000;

    public CallableTask(String name) {
        this.name = name;
    }

    public CallableTask(String name, int sllepMs) {
        this.name = name;
        this.sllepMs = sllepMs;
    }

    @Override
    public Integer call() throws Exception {
        log("Execute " + name);
        sleep(sllepMs);
        log("Complete " + name);
        return sllepMs;
    }
}
