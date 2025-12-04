package zuoye;

import java.util.Arrays;
import java.util.Scanner;

public class MergeSortedArrays {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 合并两个有序数组演示 ===");

        // 输入第一个有序数组
        System.out.print("请输入第一个有序数组的大小: ");
        int size1 = scanner.nextInt();
        int[] array1 = new int[size1];

        System.out.println("请输入第一个有序数组（升序）的 " + size1 + " 个整数:");
        for (int i = 0; i < size1; i++) {
            System.out.print("元素 " + (i + 1) + ": ");
            array1[i] = scanner.nextInt();
        }

        // 验证第一个数组是否有序
        if (!isSorted(array1)) {
            System.out.println("警告: 第一个数组不是有序的，将自动排序");
            Arrays.sort(array1);
            System.out.println("排序后: " + Arrays.toString(array1));
        }

        // 输入第二个有序数组
        System.out.print("\n请输入第二个有序数组的大小: ");
        int size2 = scanner.nextInt();
        int[] array2 = new int[size2];

        System.out.println("请输入第二个有序数组（升序）的 " + size2 + " 个整数:");
        for (int i = 0; i < size2; i++) {
            System.out.print("元素 " + (i + 1) + ": ");
            array2[i] = scanner.nextInt();
        }

        // 验证第二个数组是否有序
        if (!isSorted(array2)) {
            System.out.println("警告: 第二个数组不是有序的，将自动排序");
            Arrays.sort(array2);
            System.out.println("排序后: " + Arrays.toString(array2));
        }

        System.out.println("\n=== 输入数组 ===");
        System.out.println("数组1: " + Arrays.toString(array1));
        System.out.println("数组2: " + Arrays.toString(array2));

        // 合并两个有序数组
        int[] mergedArray = mergeSortedArrays(array1, array2);

        System.out.println("\n=== 合并结果 ===");
        System.out.println("合并后的有序数组: " + Arrays.toString(mergedArray));

        // 使用其他方法合并
        System.out.println("\n=== 其他合并方法 ===");
        int[] mergedArray2 = mergeWithSystemArrayCopy(array1, array2);
        System.out.println("使用System.arraycopy合并: " + Arrays.toString(mergedArray2));

        // 使用Java 8 Stream API合并
        System.out.println("\n=== 使用Stream API合并 ===");
        int[] mergedStream = mergeWithStream(array1, array2);
        System.out.println("使用Stream API合并: " + Arrays.toString(mergedStream));

        scanner.close();
    }

    // 合并两个有序数组的核心方法
    public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;
        int[] result = new int[n1 + n2];

        int i = 0, j = 0, k = 0;

        // 比较两个数组的元素，将较小的放入结果数组
        while (i < n1 && j < n2) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }

        // 复制剩余元素（如果arr1还有剩余）
        while (i < n1) {
            result[k++] = arr1[i++];
        }

        // 复制剩余元素（如果arr2还有剩余）
        while (j < n2) {
            result[k++] = arr2[j++];
        }

        return result;
    }

    // 使用System.arraycopy合并数组
    public static int[] mergeWithSystemArrayCopy(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];

        // 先合并两个数组
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);

        // 然后排序
        Arrays.sort(result);

        return result;
    }

    // 使用Java 8 Stream API合并数组
    public static int[] mergeWithStream(int[] arr1, int[] arr2) {
        return java.util.stream.Stream.of(
                        Arrays.stream(arr1).boxed(),
                        Arrays.stream(arr2).boxed()
                )
                .flatMap(s -> s)
                .sorted()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    // 检查数组是否有序（升序）
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // 扩展：合并三个有序数组
    public static int[] mergeThreeSortedArrays(int[] arr1, int[] arr2, int[] arr3) {
        // 先合并前两个，再合并第三个
        int[] temp = mergeSortedArrays(arr1, arr2);
        return mergeSortedArrays(temp, arr3);
    }
}