package org.example.thread.bounded;

import static org.example.util.MyLogger.log;

public class ConsumerTask implements Runnable {
    private BoundedQueue queue;

    public ConsumerTask(BoundedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        log("[Trying Consume]     ? < - " + queue);
        String data = queue.take();
        log("[Complete Consume] " + data + " < - " + queue);
    }
}
