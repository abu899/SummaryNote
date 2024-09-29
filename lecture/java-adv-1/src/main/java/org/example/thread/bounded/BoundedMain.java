package org.example.thread.bounded;

import java.util.ArrayList;
import java.util.List;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class BoundedMain {
    public static void main(String[] args) {
        // 1. select BoundedQueue implementation

        BoundedQueue queue = new BoundedQueueV1(2);

        // 2. Producer, Consumer execute order
        producerFirst(queue);
        consumerFirst(queue);
    }

    private static void producerFirst(BoundedQueue queue) throws UnsupportedOperationException {
        log("== [Producer First] Start, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startProducer(queue, threads);
        printAllState(queue, threads);
        startConsumer(queue, threads);
        printAllState(queue, threads);
        log("== [Producer First] End, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void consumerFirst(BoundedQueue queue) {
        log("== [Consumer First] Start, " + queue.getClass().getSimpleName() + " ==");
        List<Thread> threads = new ArrayList<>();
        startConsumer(queue, threads);
        printAllState(queue, threads);
        startProducer(queue, threads);
        printAllState(queue, threads);
        log("== [Consumer First] End, " + queue.getClass().getSimpleName() + " ==");
    }

    private static void startProducer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("Start Producer");
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new ProducerTask(queue, "data-" + i), "producer-" + i);
            threads.add(producer);
            producer.start();
            sleep(100);
        }
    }

    private static void printAllState(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("Current state, Queue: " + queue);
        for (Thread thread : threads) {
            log("Thread: " + thread.getName() + ", State: " + thread.getState());
        }
    }

    private static void startConsumer(BoundedQueue queue, List<Thread> threads) {
        System.out.println();
        log("Start Consumer");
        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(new ConsumerTask(queue), "consumer-" + i);
            threads.add(consumer);
            consumer.start();
            sleep(100);
        }
    }
}
