package zuoye;

import java.util.*;

public class RemoveDuplicates {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 集合去重演示 ===");
        System.out.println("请输入一组数字（用空格分隔，输入'end'结束）:");

        List<Integer> numbers = new ArrayList<>();

        // 读取用户输入
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                numbers.add(scanner.nextInt());
            } else {
                String input = scanner.next();
                if (input.equalsIgnoreCase("end")) {
                    break;
                } else {
                    System.out.println("跳过无效输入: " + input);
                }
            }
        }

        System.out.println("\n=== 原始数据 ===");
        System.out.println("原始列表: " + numbers);
        System.out.println("原始列表大小: " + numbers.size());

        // 方法1：使用HashSet去重（不保持顺序）
        System.out.println("\n=== 方法1：使用HashSet去重 ===");
        Set<Integer> hashSet = new HashSet<>(numbers);
        System.out.println("去重后（HashSet，不保持顺序）: " + hashSet);

        // 方法2：使用LinkedHashSet去重（保持插入顺序）
        System.out.println("\n=== 方法2：使用LinkedHashSet去重 ===");
        Set<Integer> linkedHashSet = new LinkedHashSet<>(numbers);
        System.out.println("去重后（LinkedHashSet，保持顺序）: " + linkedHashSet);

        // 方法3：使用TreeSet去重（自动排序）
        System.out.println("\n=== 方法3：使用TreeSet去重 ===");
        Set<Integer> treeSet = new TreeSet<>(numbers);
        System.out.println("去重后（TreeSet，自动排序）: " + treeSet);

        // 方法4：使用Java 8 Stream API去重
        System.out.println("\n=== 方法4：使用Stream API去重 ===");
        List<Integer> streamResult = numbers.stream()
                .distinct()
                .collect(java.util.stream.Collectors.toList());
        System.out.println("去重后（Stream API）: " + streamResult);

        // 方法5：手动去重算法
        System.out.println("\n=== 方法5：手动去重算法 ===");
        List<Integer> manualResult = removeDuplicatesManually(numbers);
        System.out.println("去重后（手动算法）: " + manualResult);

        // 扩展：字符串去重示例
        System.out.println("\n=== 扩展：字符串去重示例 ===");
        List<String> words = Arrays.asList("apple", "banana", "apple", "orange", "banana", "grape");
        System.out.println("原始字符串列表: " + words);

        List<String> uniqueWords = words.stream()
                .distinct()
                .collect(java.util.stream.Collectors.toList());
        System.out.println("去重后字符串列表: " + uniqueWords);

        scanner.close();
    }

    // 手动去重方法
    public static List<Integer> removeDuplicatesManually(List<Integer> list) {
        List<Integer> result = new ArrayList<>();

        for (Integer num : list) {
            if (!result.contains(num)) {
                result.add(num);
            }
        }

        return result;
    }

    // 附加：使用HashSet统计重复元素
    public static void findDuplicates(List<Integer> list) {
        Set<Integer> uniqueElements = new HashSet<>();
        Set<Integer> duplicateElements = new HashSet<>();

        for (Integer num : list) {
            if (!uniqueElements.add(num)) {
                duplicateElements.add(num);
            }
        }

        System.out.println("重复的元素: " + duplicateElements);
    }
}