package zuoye;

import java.util.LinkedList;
import java.util.Random;

public class ProducerConsumer {

    public static void main(String[] args) {
        System.out.println("=== 生产者-消费者模型演示 ===");
        System.out.println("缓冲区容量: 5");
        System.out.println("生产者数量: 2");
        System.out.println("消费者数量: 3");
        System.out.println("运行时间: 10秒\n");

        // 创建缓冲区
        Buffer buffer = new Buffer(5);

        // 创建并启动生产者线程
        Thread producer1 = new Thread(new Producer(buffer, "生产者1"), "Producer-1");
        Thread producer2 = new Thread(new Producer(buffer, "生产者2"), "Producer-2");

        // 创建并启动消费者线程
        Thread consumer1 = new Thread(new Consumer(buffer, "消费者1"), "Consumer-1");
        Thread consumer2 = new Thread(new Consumer(buffer, "消费者2"), "Consumer-2");
        Thread consumer3 = new Thread(new Consumer(buffer, "消费者3"), "Consumer-3");

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();

        // 运行10秒后停止
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 停止所有线程
        producer1.interrupt();
        producer2.interrupt();
        consumer1.interrupt();
        consumer2.interrupt();
        consumer3.interrupt();

        System.out.println("\n=== 程序运行结束 ===");
    }
}

// 缓冲区类
class Buffer {
    private LinkedList<Integer> queue = new LinkedList<>();
    private int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    // 生产方法（同步）
    public synchronized void produce(int value, String producerName) throws InterruptedException {
        // 如果缓冲区已满，等待
        while (queue.size() == capacity) {
            System.out.println(producerName + ": 缓冲区已满，等待中...");
            wait();
        }

        // 生产数据
        queue.add(value);
        System.out.println(producerName + " 生产: " + value + " | 缓冲区大小: " + queue.size());

        // 通知所有等待的消费者
        notifyAll();
    }

    // 消费方法（同步）
    public synchronized int consume(String consumerName) throws InterruptedException {
        // 如果缓冲区为空，等待
        while (queue.isEmpty()) {
            System.out.println(consumerName + ": 缓冲区为空，等待中...");
            wait();
        }

        // 消费数据
        int value = queue.removeFirst();
        System.out.println(consumerName + " 消费: " + value + " | 缓冲区大小: " + queue.size());

        // 通知所有等待的生产者
        notifyAll();

        return value;
    }

    // 获取当前缓冲区大小
    public synchronized int size() {
        return queue.size();
    }
}

// 生产者类
class Producer implements Runnable {
    private Buffer buffer;
    private String name;
    private Random random = new Random();

    public Producer(Buffer buffer, String name) {
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            int productId = 1;
            while (!Thread.currentThread().isInterrupted()) {
                // 生产一个产品
                int product = productId++;
                buffer.produce(product, name);

                // 随机休眠一段时间，模拟生产过程
                Thread.sleep(random.nextInt(1000) + 500);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " 被中断，停止生产");
        }
    }
}

// 消费者类
class Consumer implements Runnable {
    private Buffer buffer;
    private String name;
    private Random random = new Random();

    public Consumer(Buffer buffer, String name) {
        this.buffer = buffer;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // 消费一个产品
                int product = buffer.consume(name);

                // 模拟消费过程
                Thread.sleep(random.nextInt(1000) + 300);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " 被中断，停止消费");
        }
    }
}