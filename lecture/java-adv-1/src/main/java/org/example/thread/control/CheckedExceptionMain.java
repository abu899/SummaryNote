package org.example.thread.control;

import org.example.util.ThreadUtils;

public class CheckedExceptionMain {
    public static void main(String[] args) throws Exception {
        throw new Exception();
    }

    static class CheckedRunnable implements Runnable {
        @Override
        public void run() {
//            throw new Exception(); // 불가능
            ThreadUtils.sleep(1000);
        }
    }
}
