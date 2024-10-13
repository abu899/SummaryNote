package org.example.thread.cas.spinlock;

import static org.example.util.MyLogger.log;
import static org.example.util.ThreadUtils.sleep;

public class SpinLockBad {
    private volatile boolean lock = false;

    public void lock() {
        log("락 획득 시도");
        while (true) {
            if (!lock) {
                sleep(100);
                lock = true;
                break;
            } else {
                log("락 획득 실패로 락 획득 스핀 대기");
            }
        }
        log("락 획득 성공");
    }

    public void unlock() {
        lock = false;
        log("락 해제");
    }
}
