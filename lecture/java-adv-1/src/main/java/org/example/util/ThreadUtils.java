package org.example.util;

import static org.example.util.MyLogger.log;

public abstract class ThreadUtils {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log("interrupted occurred" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
