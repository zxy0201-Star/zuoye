package zuoye;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private String accountId;
    private String username;
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(String accountId, String username, double initialBalance) {
        this.accountId = accountId;
        this.username = username;
        this.balance = initialBalance;
    }

    // 存款
    public void deposit(double amount) {
        lock.lock();
        try {
            if (amount > 0) {
                balance += amount;
                System.out.println(Thread.currentThread().getName() + " 存款成功: " + amount + ", 余额: " + balance);
            } else {
                System.out.println("存款金额必须大于0");
            }
        } finally {
            lock.unlock();
        }
    }

    // 取款
    public void withdraw(double amount) {
        lock.lock();
        try {
            if (amount > 0) {
                if (amount <= balance) {
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + " 取款成功: " + amount + ", 余额: " + balance);
                } else {
                    System.out.println(Thread.currentThread().getName() + " 取款失败: 余额不足");
                }
            } else {
                System.out.println("取款金额必须大于0");
            }
        } finally {
            lock.unlock();
        }
    }

    // 查询余额
    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return String.format("账户ID: %s, 用户名: %s, 余额: %.2f", accountId, username, balance);
    }
}

 class BankAccountSystem {
    private static final String FILE_NAME = "accounts.txt";
    private static Map<String, BankAccount> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 从文件加载账户信息
        loadAccountsFromFile();

        while (true) {
            System.out.println("\n=== 银行账户管理系统 ===");
            System.out.println("1. 创建账户");
            System.out.println("2. 存款");
            System.out.println("3. 取款");
            System.out.println("4. 查询余额");
            System.out.println("5. 查看所有账户");
            System.out.println("6. 模拟多线程操作");
            System.out.println("7. 保存账户信息到文件");
            System.out.println("8. 退出");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    queryBalance();
                    break;
                case 5:
                    displayAllAccounts();
                    break;
                case 6:
                    simulateMultiThreadOperations();
                    break;
                case 7:
                    saveAccountsToFile();
                    break;
                case 8:
                    System.out.println("感谢使用银行账户管理系统!");
                    saveAccountsToFile(); // 退出前保存
                    scanner.close();
                    return;
                default:
                    System.out.println("无效的选择，请重新输入!");
            }
        }
    }

    private static void createAccount() {
        System.out.print("请输入账户ID: ");
        String accountId = scanner.nextLine();

        if (accounts.containsKey(accountId)) {
            System.out.println("账户ID已存在!");
            return;
        }

        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();

        System.out.print("请输入初始余额: ");
        double initialBalance = scanner.nextDouble();

        BankAccount account = new BankAccount(accountId, username, initialBalance);
        accounts.put(accountId, account);
        System.out.println("账户创建成功!");
    }

    private static void deposit() {
        System.out.print("请输入账户ID: ");
        String accountId = scanner.nextLine();

        BankAccount account = accounts.get(accountId);
        if (account == null) {
            System.out.println("账户不存在!");
            return;
        }

        System.out.print("请输入存款金额: ");
        double amount = scanner.nextDouble();

        account.deposit(amount);
    }

    private static void withdraw() {
        System.out.print("请输入账户ID: ");
        String accountId = scanner.nextLine();

        BankAccount account = accounts.get(accountId);
        if (account == null) {
            System.out.println("账户不存在!");
            return;
        }

        System.out.print("请输入取款金额: ");
        double amount = scanner.nextDouble();

        account.withdraw(amount);
    }

    private static void queryBalance() {
        System.out.print("请输入账户ID: ");
        String accountId = scanner.nextLine();

        BankAccount account = accounts.get(accountId);
        if (account == null) {
            System.out.println("账户不存在!");
            return;
        }

        System.out.println("账户余额: " + account.getBalance());
    }

    private static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("暂无账户信息");
            return;
        }

        System.out.println("\n=== 所有账户列表 ===");
        for (BankAccount account : accounts.values()) {
            System.out.println(account);
        }
    }

    private static void simulateMultiThreadOperations() {
        if (accounts.isEmpty()) {
            System.out.println("没有账户，请先创建账户");
            return;
        }

        System.out.print("请输入要测试的账户ID: ");
        String accountId = scanner.nextLine();

        BankAccount account = accounts.get(accountId);
        if (account == null) {
            System.out.println("账户不存在!");
            return;
        }

        System.out.println("开始模拟多线程操作...");

        // 创建多个线程同时对同一个账户进行操作
        Thread[] threads = new Thread[5];

        // 3个存款线程
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    account.deposit(100);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "存款线程-" + (i + 1));
        }

        // 2个取款线程
        for (int i = 3; i < 5; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    account.withdraw(50);
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "取款线程-" + (i - 2));
        }

        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("多线程操作完成，最终余额: " + account.getBalance());
    }

    private static void saveAccountsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (BankAccount account : accounts.values()) {
                writer.println(account.getAccountId() + "," + account.getUsername() + "," + account.getBalance());
            }
            System.out.println("账户信息已保存到文件: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("保存文件失败: " + e.getMessage());
        }
    }

    private static void loadAccountsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("文件不存在，将创建新文件");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String accountId = parts[0];
                    String username = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accounts.put(accountId, new BankAccount(accountId, username, balance));
                }
            }
            System.out.println("从文件加载了 " + accounts.size() + " 个账户信息");
        } catch (IOException e) {
            System.out.println("读取文件失败: " + e.getMessage());
        }
    }
}