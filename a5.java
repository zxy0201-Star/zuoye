package zuoye;
import java.util.Scanner;
public class a5 { public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("请输入年份: ");
    int year = scanner.nextInt();

    boolean isLeapYear;

    // 判断闰年的规则：
    // 1. 能被400整除的是闰年
    // 2. 能被4整除但不能被100整除的是闰年
    // 3. 其他情况都不是闰年
    if (year % 400 == 0) {
        isLeapYear = true;
    } else if (year % 100 == 0) {
        isLeapYear = false;
    } else if (year % 4 == 0) {
        isLeapYear = true;
    } else {
        isLeapYear = false;
    }

    if (isLeapYear) {
        System.out.println(year + "年是闰年");
    } else {
        System.out.println(year + "年不是闰年");
    }

    scanner.close();
}
}
