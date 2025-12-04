package zuoye;

public class a7 {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public a7(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        System.out.println("账户创建成功!");
        displayAccountInfo();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("存款成功! 存款金额: ¥" + amount);
            System.out.println("当前余额: ¥" + balance);
        } else {
            System.out.println("存款失败: 存款金额必须大于0");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                System.out.println("取款成功! 取款金额: ¥" + amount);
                System.out.println("当前余额: ¥" + balance);
            } else {
                System.out.println("取款失败: 余额不足! 当前余额: ¥" + balance);
            }
        } else {
            System.out.println("取款失败: 取款金额必须大于0");
        }
    }

    public double checkBalance() {
        return balance;
    }

    public void displayAccountInfo() {
        System.out.println("========== 账户信息 ==========");
        System.out.println("账户号码: " + accountNumber);
        System.out.println("账户持有人: " + accountHolder);
        System.out.println("账户余额: ¥" + balance);
        System.out.println("=============================");
    }

    public static void main(String[] args) {
        a7 myAccount = new a7("622848001234567890", "张三", 1000.0);

        myAccount.deposit(500.0);
        myAccount.withdraw(200.0);
        myAccount.withdraw(1500.0);  // 测试余额不足的情况

        System.out.println("当前余额查询: ¥" + myAccount.checkBalance());
    }
}
