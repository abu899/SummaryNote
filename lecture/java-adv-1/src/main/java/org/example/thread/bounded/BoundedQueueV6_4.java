package org.example.thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.example.util.MyLogger.log;

/**
 * BlockingQueue의 여러 기능들 확인 - add, remove 예외 발생
 */
public class BoundedQueueV6_4 implements BoundedQueue {
    private BlockingQueue<String> queue;

    public BoundedQueueV6_4(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    @Override
    public void put(String data) {
        queue.add(data); // IllegalStateException 발생
    }

    @Override
    public String take() {
        return queue.remove(); // IllegalStateException 발생
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
