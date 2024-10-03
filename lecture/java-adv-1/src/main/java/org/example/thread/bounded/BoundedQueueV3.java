package org.example.thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class BoundedQueueV3 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {
        while (queue.size() == max) {
            log("[put] queue is full, producer wait");
            try {
                wait(); // RUNNABLE -> WAITING, release lock
                log("[put] producer wake up");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        queue.offer(data);
        log("[put] producer save data, call notify()");
        notify(); // WAITING -> BLOCKED
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] queue is empty, consumer wait");
            try {
                wait(); // RUNNABLE -> WAITING, release lock
                log("[take] consumer wake up");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        String data = queue.poll();
        log("[take] consumer get data, call notify()");
        notify(); // WAITING -> BLOCKED
        return data;
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
