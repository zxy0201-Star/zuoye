package zuoye;
import java.util.Scanner;

public class a3 { public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("请输入圆的半径: ");
    double radius = scanner.nextDouble();

    if (radius < 0) {
        System.out.println("半径不能为负数");
    } else {
        double area = Math.PI * radius * radius;
        System.out.printf("圆的面积是: %.2f\n", area);
    }

    scanner.close();
}
}

