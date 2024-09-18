package org.example.thread.start;

import static org.example.util.MyLogger.log;

public class InnerRunnableMainV4 {
    public static void main(String[] args) {
        log("main() start");
        Thread thread = new Thread(() -> log("MyRunnable.run()"));
        thread.start();
        log("main() end");
    }
}
