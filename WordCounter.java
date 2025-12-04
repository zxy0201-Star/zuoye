package zuoye;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class WordCounter {

    // 缓存正则表达式以提高性能
    private static final Pattern WORD_PATTERN = Pattern.compile("[\\p{Alpha}]+");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 文件单词统计程序 ===");

        String filePath;
        if (args.length > 0) {
            filePath = args[0];
            System.out.println("使用命令行参数指定的文件: " + filePath);
        } else {
            System.out.print("请输入文件路径（或按Enter使用默认文件）: ");
            filePath = scanner.nextLine();

            if (filePath.isEmpty()) {
                // 创建示例文件
                filePath = "sample.txt";
                try {
                    createSampleFile(filePath);
                } catch (IOException e) {
                    System.err.println("创建示例文件失败: " + e.getMessage());
                    return;
                }
                System.out.println("使用示例文件: " + filePath);
            }
        }

        // 增加对文件路径的非空检查
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("文件路径不能为空");
            scanner.close();
            return;
        }

        try {
            // 基本单词统计
            System.out.println("\n=== 基本统计 ===");
            int totalWords = countWordsBasic(filePath);
            System.out.println("单词总数: " + totalWords);

            // 详细统计
            System.out.println("\n=== 详细统计 ===");
            Map<String, Integer> wordFrequency = countWordFrequency(filePath);
            System.out.println("不同单词数: " + wordFrequency.size());

            // 显示最常用的10个单词
            System.out.println("\n=== 最常用的10个单词 ===");
            displayTopWords(wordFrequency, 10);

            // 显示最长的5个单词
            System.out.println("\n=== 最长的5个单词 ===");
            displayLongestWords(wordFrequency, 5);

            // 统计字母频率
            System.out.println("\n=== 字母频率统计 ===");
            Map<Character, Integer> letterFrequency = countLetterFrequency(filePath);
            System.out.println("不同字母数: " + letterFrequency.size());

            // 显示字母频率
            displayLetterFrequency(letterFrequency);

            // 保存统计结果
            System.out.println("\n=== 保存统计结果 ===");
            saveStatistics(filePath, totalWords, wordFrequency);

        } catch (FileNotFoundException e) {
            System.out.println("文件未找到: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("文件操作错误: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("程序执行出错: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // 创建示例文件
    private static void createSampleFile(String filePath) throws IOException {
        String content = "Java is a high-level, class-based, object-oriented programming language " +
                "that is designed to have as few implementation dependencies as possible. " +
                "It is a general-purpose programming language intended to let programmers " +
                "write once, run anywhere (WORA), meaning that compiled Java code can run " +
                "on all platforms that support Java without the need for recompilation. " +
                "Java applications are typically compiled to bytecode that can run on any " +
                "Java virtual machine (JVM) regardless of the underlying computer architecture. " +
                "The syntax of Java is similar to C and C++, but has fewer low-level facilities " +
                "than either of them. The Java runtime provides dynamic capabilities " +
                "(such as reflection and runtime code modification) that are typically not " +
                "available in traditional compiled languages.";

        // 使用try-with-resources确保资源正确关闭
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(content);
        }
    }

    // 基本单词计数（简单方法）
    public static int countWordsBasic(String filePath) throws IOException {
        int wordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // 使用正则表达式分割单词
                String[] words = line.split("[\\s\\p{Punct}]+");

                for (String word : words) {
                    if (!word.isEmpty() && WORD_PATTERN.matcher(word).matches()) {
                        wordCount++;
                    }
                }
            }
        }

        return wordCount;
    }

    // 统计单词频率
    public static Map<String, Integer> countWordFrequency(String filePath) throws IOException {
        Map<String, Integer> frequencyMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // 转换为小写并分割单词
                String[] words = line.toLowerCase().split("[\\s\\p{Punct}]+");

                for (String word : words) {
                    if (!word.isEmpty() && WORD_PATTERN.matcher(word).matches()) {
                        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
        }

        return frequencyMap;
    }

    // 显示最常用的单词
    public static void displayTopWords(Map<String, Integer> wordFrequency, int topN) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequency.entrySet());

        // 按频率降序排序
        sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        System.out.println("排名\t单词\t\t频率");
        System.out.println("--------------------------------");

        int count = Math.min(topN, sortedEntries.size());
        for (int i = 0; i < count; i++) {
            Map.Entry<String, Integer> entry = sortedEntries.get(i);
            System.out.printf("%d\t%-15s\t%d\n", i + 1, entry.getKey(), entry.getValue());
        }
    }

    // 显示最长的单词
    public static void displayLongestWords(Map<String, Integer> wordFrequency, int topN) {
        // 使用Stream API简化排序逻辑
        List<String> sortedWords = wordFrequency.keySet().stream()
                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
                .limit(topN)
                .toList();

        System.out.println("排名\t单词\t\t\t长度\t频率");
        System.out.println("----------------------------------------");

        int count = Math.min(topN, sortedWords.size());
        for (int i = 0; i < count; i++) {
            String word = sortedWords.get(i);
            int length = word.length();
            int frequency = wordFrequency.get(word);
            System.out.printf("%d\t%-20s\t%d\t%d\n", i + 1, word, length, frequency);
        }
    }

    // 统计字母频率
    public static Map<Character, Integer> countLetterFrequency(String filePath) throws IOException {
        Map<Character, Integer> letterMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                for (char c : line.toLowerCase().toCharArray()) {
                    if (Character.isLetter(c)) {
                        letterMap.put(c, letterMap.getOrDefault(c, 0) + 1);
                    }
                }
            }
        }

        return letterMap;
    }

    // 显示字母频率
    public static void displayLetterFrequency(Map<Character, Integer> letterFrequency) {
        List<Character> sortedLetters = new ArrayList<>(letterFrequency.keySet());

        // 按字母顺序排序
        Collections.sort(sortedLetters);

        System.out.println("字母\t频率\t百分比");
        System.out.println("----------------------");

        int totalLetters = letterFrequency.values().stream().mapToInt(Integer::intValue).sum();

        for (char letter : sortedLetters) {
            int frequency = letterFrequency.get(letter);
            double percentage = (frequency * 100.0) / totalLetters;
            System.out.printf("%c\t%d\t%.2f%%\n", letter, frequency, percentage);
        }

        System.out.println("----------------------");
        System.out.println("总计:\t" + totalLetters + "\t100.00%");
    }

    // 保存统计结果
    public static void saveStatistics(String filePath, int totalWords,
                                      Map<String, Integer> wordFrequency) throws IOException {
        // 处理不同扩展名的情况
        String statsFilePath;
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex > 0) {
            statsFilePath = filePath.substring(0, lastDotIndex) + "_stats" + filePath.substring(lastDotIndex);
        } else {
            statsFilePath = filePath + "_stats.txt";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(statsFilePath))) {
            writer.println("=== 文件单词统计结果 ===");
            writer.println("文件名: " + filePath);
            writer.println("统计时间: " + new Date());
            writer.println();
            writer.println("单词总数: " + totalWords);
            writer.println("不同单词数: " + wordFrequency.size());
            writer.println();

            writer.println("=== 单词频率 ===");
            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequency.entrySet());
            sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                writer.printf("%-20s\t%d\n", entry.getKey(), entry.getValue());
            }

            System.out.println("统计结果已保存到: " + statsFilePath);
        }
    }
}
