package org.example.thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.example.util.MyLogger.log;

public class MyPrinterV2 {
    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread thread = new Thread(printer, "Printer");
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            log("프린터할 문서 입력. 종료(q) : ");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                thread.interrupt();
                break;
            }
            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    if (jobQueue.isEmpty()) {
                        continue;
                    }

                    String job = jobQueue.poll();
                    log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
                    Thread.sleep(3000);
                    log("출력 완료");
                }
            } catch (InterruptedException e) {
                log("Interrupted");
                log("프린터 종료");
            }
        }

        public void addJob(String job) {
            jobQueue.offer(job);
        }
    }
}
