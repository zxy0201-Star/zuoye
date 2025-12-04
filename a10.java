package zuoye;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class a10 {
    private double balance;
    private final String accountHolder; // 标记为 final

    public a10() {
        this.balance = 0.0;
        this.accountHolder = "Unknown";
    }

    public a10(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(accountHolder + " 存款成功! 存入金额: ¥" + amount);
            System.out.println("当前余额: ¥" + balance);
        } else {
            System.out.println("存款失败: 金额必须大于0");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                System.out.println(accountHolder + " 取款成功! 取出金额: ¥" + amount);
                System.out.println("当前余额: ¥" + balance);
            } else {
                System.out.println("取款失败: 余额不足! 当前余额: ¥" + balance);
            }
        } else {
            System.out.println("取款失败: 金额必须大于0");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void displayInfo() {
        System.out.println("账户持有人: " + accountHolder);
        System.out.println("账户余额: ¥" + balance);
    }
}

class ReflectionDemo {
    private static final Logger logger = Logger.getLogger(ReflectionDemo.class.getName());

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) { // 使用 try-with-resources 自动关闭资源
            System.out.println("=== 反射机制演示 ===");

            // 1. 获取BankAccountForReflection类的Class对象
            Class<?> bankAccountClass = Class.forName("zuoye.a10"); // 添加包名避免歧义
            System.out.println("获取到类: " + bankAccountClass.getName());

            // 2. 获取类的所有构造方法
            System.out.println("\n可用的构造方法:");
            Constructor<?>[] constructors = bankAccountClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println("  " + constructor);
            }

            // 3. 选择构造方法创建对象
            System.out.print("\n请选择构造方法 (1-无参构造, 2-有参构造): ");
            int constructorChoice = scanner.nextInt();

            Object bankAccount;

            if (constructorChoice == 1) {
                // 使用无参构造方法
                Constructor<?> noArgConstructor = bankAccountClass.getConstructor();
                bankAccount = noArgConstructor.newInstance();
                System.out.println("使用无参构造方法创建对象成功");
            } else {
                // 使用有参构造方法
                scanner.nextLine(); // 清除换行符
                System.out.print("请输入账户持有人姓名: ");
                String name = scanner.nextLine();

                System.out.print("请输入初始余额: ");
                double balance = scanner.nextDouble();

                Constructor<?> argConstructor = bankAccountClass.getConstructor(String.class, double.class);
                bankAccount = argConstructor.newInstance(name, balance);
                System.out.println("使用有参构造方法创建对象成功");
            }

            // 4. 获取deposit方法
            Method depositMethod = bankAccountClass.getMethod("deposit", double.class);
            System.out.println("\n获取到deposit方法: " + depositMethod);

            // 5. 通过反射调用deposit方法
            System.out.print("\n请输入存款金额: ");
            double depositAmount = scanner.nextDouble();

            System.out.println("通过反射调用deposit方法...");
            depositMethod.invoke(bankAccount, depositAmount);

            // 6. 获取getBalance方法并调用
            Method getBalanceMethod = bankAccountClass.getMethod("getBalance");
            System.out.println("\n获取到getBalance方法: " + getBalanceMethod);

            double balance = (double) getBalanceMethod.invoke(bankAccount);
            System.out.println("通过反射获取余额: ¥" + balance);

            // 7. 获取所有方法
            System.out.println("\nBankAccountForReflection类的所有方法:");
            Method[] methods = bankAccountClass.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("  " + method.getName() + " -> 返回类型: " + method.getReturnType());
            }

        } catch (ClassNotFoundException e) {
            System.out.println("未找到类: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("反射操作错误: " + e.getMessage());
            logger.log(Level.SEVERE, "发生异常", e); // 替代 printStackTrace()
        }

        System.out.println("\n反射演示结束");
    }
}
