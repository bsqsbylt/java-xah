package com.github.vevc;

import java.io.IOException;

public class Keeper {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Java Keeper started. VPS is online.");
        System.out.println("Memory: " + 
            Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB allocated");
        
        // 启动心跳监控线程（非守护线程）
        Thread heartbeatThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            int count = 0;
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(60_000);
                    count++;
                    long uptime = (System.currentTimeMillis() - startTime) / 1000;
                    System.out.printf("[%d] Heartbeat #%d - Uptime: %d seconds%n",
                        System.currentTimeMillis(), count, uptime);
                } catch (InterruptedException e) {
                    System.out.println("Heartbeat interrupted, shutting down.");
                    break;
                }
            }
        });
        heartbeatThread.setDaemon(false); // 关键：非守护线程
        heartbeatThread.start();
        
        // 关键：启动 bash 进程保持运行（与 java-xah 一样）
        ProcessBuilder pb = new ProcessBuilder("bash");
        pb.inheritIO();
        System.out.println("Starting bash to keep process alive...");
        Process process = pb.start();
        int exitCode = process.waitFor();
        System.out.println("Bash exited with code: " + exitCode);
    }
}
