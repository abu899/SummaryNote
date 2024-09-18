package org.example.thread.start;

import static org.example.util.MyLogger.log;

public class InnerRunnableMainV2 {
    public static void main(String[] args) {
        log("main() start");

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                log("MyRunnable.run()");
            }
        };

        Thread thread = new Thread(myRunnable);
        thread.start();
        log("main() end");
    }
}
