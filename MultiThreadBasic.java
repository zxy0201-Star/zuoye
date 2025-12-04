package zuoye;

public class MultiThreadBasic {

    public static void main(String[] args) {
        System.out.println("=== 多线程基本操作演示 ===");
        System.out.println("主线程开始执行...");

        // 创建并启动第一个线程（输出数字）
        Thread thread1 = new Thread(new NumberPrinter(), "数字线程");
        thread1.start();

        // 创建并启动第二个线程（输出字母）
        Thread thread2 = new Thread(new LetterPrinter(), "字母线程");
        thread2.start();

        // 使用Lambda表达式创建线程
        Thread thread3 = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + ": 特殊字符 " + i);
                    Thread.sleep(300); // 休眠300毫秒
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " 被中断");
            }
        }, "特殊字符线程");

        thread3.start();

        // 主线程也执行一些操作
        try {
            for (int i = 1; i <= 3; i++) {
                System.out.println("主线程: 执行任务 " + i);
                Thread.sleep(500); // 休眠500毫秒
            }
        } catch (InterruptedException e) {
            System.out.println("主线程被中断");
        }

        // 等待所有线程完成
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("等待线程时被中断");
        }

        System.out.println("所有线程执行完毕，主线程结束");
    }
}

// 输出数字的线程任务
class NumberPrinter implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                Thread.sleep(200); // 休眠200毫秒
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " 被中断");
        }
    }
}

// 输出字母的线程任务
class LetterPrinter implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            for (char c = 'A'; c <= 'J'; c++) {
                System.out.println(Thread.currentThread().getName() + ": " + c);
                Thread.sleep(250); // 休眠250毫秒
            }
            System.out.println(Thread.currentThread().getName() + " 执行完毕");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " 被中断");
        }
    }
}