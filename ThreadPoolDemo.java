package zuoye;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        // 创建一个固定大小的线程池，包含3个线程
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 提交10个任务给线程池
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("任务 " + taskId + " 正在执行，线程: " + Thread.currentThread().getName());
                try {
                    // 模拟任务执行时间
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务 " + taskId + " 执行完成");
            });
        }

        // 关闭线程池，不再接受新任务
        executor.shutdown();

        try {
            // 等待所有任务完成，最多等待60秒
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("仍有任务未完成，强制关闭");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("所有任务执行完毕");
    }
}