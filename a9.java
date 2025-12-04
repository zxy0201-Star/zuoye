package zuoye;
import java.util.Scanner;
 class a9 extends Exception {  public a9(String message) {
     super(message);
 }
 }

// 使用自定义异常的示例类
 class CustomExceptionDemo {

    // 检查数字是否为正数的方法
    public static void checkPositiveNumber(int number) throws a9 {
        if (number < 0) {
            throw new a9("错误: 输入了负数 " + number + "，请输入正数!");
        }
    }

    // 检查年龄是否有效的方法
    public static void checkAge(int age) throws a9 {
        if (age < 0) {
            throw new a9("错误: 年龄不能为负数! 输入值: " + age);
        } else if (age > 150) {
            throw new a9("警告: 年龄值 " + age + " 可能不合理!");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 自定义异常示例程序 ===");
        System.out.println("1. 检查正数");
        System.out.println("2. 检查年龄");
        System.out.print("请选择测试项目: ");

        int choice = scanner.nextInt();

        try {
            if (choice == 1) {
                System.out.print("请输入一个正数: ");
                int num = scanner.nextInt();
                checkPositiveNumber(num);
                System.out.println("输入正确: " + num + " 是正数");

            } else if (choice == 2) {
                System.out.print("请输入年龄: ");
                int age = scanner.nextInt();
                checkAge(age);
                System.out.println("年龄输入有效: " + age + " 岁");

            } else {
                System.out.println("无效的选择!");
            }

        } catch (a9 e) {
            System.out.println("捕获到自定义异常: " + e.getMessage());
            System.out.println("异常类型: " + e.getClass().getName());

        } catch (Exception e) {
            System.out.println("发生其他异常: " + e.getMessage());

        } finally {
            System.out.println("程序执行完毕");
            scanner.close();
        }
    }
}
