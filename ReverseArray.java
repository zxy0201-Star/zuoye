package zuoye;

import java.util.Arrays;
import java.util.Scanner;

public class ReverseArray {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 数组逆序输出演示 ===");

        System.out.print("请输入数组元素个数: ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("数组大小必须大于0");
            scanner.close();
            return;
        }

        int[] array = new int[n];

        System.out.println("请输入 " + n + " 个整数:");
        for (int i = 0; i < n; i++) {
            System.out.print("元素 " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }

        System.out.println("\n=== 原始数组 ===");
        System.out.println("数组内容: " + Arrays.toString(array));

        // 逆序输出数组（不改变原数组）
        System.out.println("\n=== 逆序输出（不改变原数组） ===");
        System.out.print("逆序输出: [");
        for (int i = n - 1; i >= 0; i--) {
            System.out.print(array[i]);
            if (i > 0) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // 逆序排列数组（改变原数组）
        System.out.println("\n=== 逆序排列（改变原数组） ===");
        reverseArray(array);
        System.out.println("逆序排列后: " + Arrays.toString(array));

        // 使用递归方式逆序输出
        System.out.println("\n=== 递归逆序输出 ===");
        System.out.print("递归逆序输出: ");
        printArrayReverseRecursive(array, 0);
        System.out.println();

        scanner.close();
    }

    // 逆序排列数组的方法（改变原数组）
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // 交换左右元素
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            left++;
            right--;
        }
    }

    // 递归逆序输出数组
    public static void printArrayReverseRecursive(int[] arr, int index) {
        if (index >= arr.length) {
            return;
        }

        // 先递归处理后面的元素
        printArrayReverseRecursive(arr, index + 1);

        // 然后输出当前元素
        System.out.print(arr[index]);
        if (index > 0) {
            System.out.print(" ");
        }
    }

    // 附加：使用Collections.reverse()处理数组（需要转换为List）
    public static void reverseWithCollections(Integer[] arr) {
        java.util.List<Integer> list = Arrays.asList(arr);
        java.util.Collections.reverse(list);
        System.out.println("使用Collections.reverse逆序: " + list);
    }
}