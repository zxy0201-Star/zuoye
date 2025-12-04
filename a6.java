package zuoye;
import java.util.Scanner;
public class a6 { public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("请输入第一个数字: ");
    double num1 = scanner.nextDouble();

    System.out.print("请输入运算符(+, -, *, /): ");
    String operator = scanner.next();

    System.out.print("请输入第二个数字: ");
    double num2 = scanner.nextDouble();

    double result = 0;
    boolean validOperation = true;

    switch (operator) {
        case "+":
            result = num1 + num2;
            break;
        case "-":
            result = num1 - num2;
            break;
        case "*":
            result = num1 * num2;
            break;
        case "/":
            if (num2 != 0) {
                result = num1 / num2;
            } else {
                System.out.println("错误: 除数不能为0!");
                validOperation = false;
            }
            break;
        default:
            System.out.println("错误: 无效的运算符!");
            validOperation = false;
    }

    if (validOperation) {
        System.out.println("计算结果: " + num1 + " " + operator + " " + num2 + " = " + result);
    }

    scanner.close();
}
}
