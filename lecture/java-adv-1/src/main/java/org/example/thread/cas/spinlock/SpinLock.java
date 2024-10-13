package org.example.thread.cas.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.example.util.MyLogger.log;

public class SpinLock {
    private final AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        log("락 획득 시도");
        while (!lock.compareAndSet(false, true)) {
            log("락 획득 실패로 락 획득 스핀 대기");
        }
        log("락 획득 성공");
    }

    public void unlock() {
        lock.set(false);
        log("락 해제");
    }
}
