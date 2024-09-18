package org.example.thread.start;

import static org.example.util.MyLogger.log;

public class InnerRunnableMainV3 {
    public static void main(String[] args) {
        log("main() start");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log("MyRunnable.run()");
            }
        });
        thread.start();

        log("main() end");
    }
}
