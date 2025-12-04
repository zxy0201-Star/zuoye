package zuoye;
import java.util.Scanner;
public class a2 { public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("请输入第一个整数: ");
    int num1 = scanner.nextInt();

    System.out.print("请输入第二个整数: ");
    int num2 = scanner.nextInt();

    int sum = num1 + num2;
    System.out.println("两个整数的和是: " + sum);

    scanner.close();
}
}


