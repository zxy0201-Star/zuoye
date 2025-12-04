package zuoye;

public class a4 { public static void main(String[] args) {
    System.out.println("99乘法表:");
    System.out.println("=====================================");

    for (int i = 1; i <= 9; i++) {
        for (int j = 1; j <= i; j++) {
            System.out.printf("%d×%d=%d\t", j, i, i * j);
        }
        System.out.println();
    }

    System.out.println("=====================================");
}
}

