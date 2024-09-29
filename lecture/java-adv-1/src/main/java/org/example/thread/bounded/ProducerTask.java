package org.example.thread.bounded;

import static org.example.util.MyLogger.log;

public class ProducerTask implements Runnable {

    private BoundedQueue queue;
    private String request;

    public ProducerTask(BoundedQueue queue, String request) {
        this.queue = queue;
        this.request = request;
    }

    @Override
    public void run() {
        log("[Trying Produce] "+ request + " -> " + queue);
        queue.put(request);
        log("[Complete Produce] "+ request + " -> " + queue);
    }
}
