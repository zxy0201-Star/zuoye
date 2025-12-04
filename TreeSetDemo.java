package zuoye;

import java.util.Scanner;
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TreeSet<Integer> treeSet = new TreeSet<>();

        System.out.println("请输入一组整数（输入-1结束）:");
        while (true) {
            int num = scanner.nextInt();
            if (num == -1) {
                break;
            }
            treeSet.add(num);
        }

        System.out.println("自动排序后的整数（升序）:");
        for (Integer num : treeSet) {
            System.out.print(num + " ");
        }
        System.out.println();

        // 获取第一个和最后一个元素
        System.out.println("最小的数: " + treeSet.first());
        System.out.println("最大的数: " + treeSet.last());

        // 获取小于某个值的元素
        System.out.print("请输入一个阈值，获取小于该值的所有数: ");
        int threshold = scanner.nextInt();
        System.out.println("小于 " + threshold + " 的数: " + treeSet.headSet(threshold));

        scanner.close();
    }
}
