package zuoye;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CharacterCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入一个字符串: ");
        String input = scanner.nextLine();

        // 使用HashMap统计每个字符的出现次数
        Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : input.toCharArray()) {
            // 只统计字母和数字，忽略空格和标点
            if (Character.isLetterOrDigit(c)) {
                charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
            }
        }

        // 输出统计结果
        System.out.println("字符出现次数统计:");
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            System.out.println("'" + entry.getKey() + "': " + entry.getValue() + "次");
        }

        scanner.close();
    }
}