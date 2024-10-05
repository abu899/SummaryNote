package org.example.thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.example.util.MyLogger.log;

/**
 * BlockingQueue의 여러 기능들 확인 - offer, poll 즉시 반환
 */
public class BoundedQueueV6_2 implements BoundedQueue {
    private BlockingQueue<String> queue;

    public BoundedQueueV6_2(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    @Override
    public void put(String data) {
        boolean result = queue.offer(data);
        log("[put] result: " + result);
    }

    @Override
    public String take() {
       return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
