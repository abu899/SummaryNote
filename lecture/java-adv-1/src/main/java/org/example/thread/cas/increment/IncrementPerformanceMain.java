package org.example.thread.cas.increment;

public class IncrementPerformanceMain {
    private static final long COUNT = 100_000_000;
    public static void main(String[] args) {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
        test(new MyAtomicInteger());
    }

    private static void test(IncrementInteger incrementInteger) {
        long start = System.currentTimeMillis();
        for (long i = 0; i < COUNT; i++) {
            incrementInteger.increment();
        }
        long end = System.currentTimeMillis();
        System.out.println(incrementInteger.getClass().getSimpleName() + " time = " + (end - start) + "ms");
    }
}
