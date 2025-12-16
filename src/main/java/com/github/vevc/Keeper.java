package com.github.vevc;

public class Keeper {
    public static void main(String[] args) {
        System.out.println("Java Keeper started. VPS is online.");
        System.out.println("Memory: " + 
            Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB allocated");
        
        long startTime = System.currentTimeMillis();
        int heartbeatCount = 0;
        
        while (true) {
            try {
                Thread.sleep(60_000); // 每分钟心跳一次
                heartbeatCount++;
                long uptime = (System.currentTimeMillis() - startTime) / 1000;
                System.out.printf("[%d] Heartbeat #%d - Uptime: %d seconds%n", 
                    System.currentTimeMillis(), heartbeatCount, uptime);
            } catch (InterruptedException e) {
                System.out.println("Interrupted, shutting down gracefully.");
                break;
            }
        }
    }
}
