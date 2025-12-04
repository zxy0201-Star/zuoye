package zuoye;

import java.io.*;
import java.util.Scanner;

public class FileReadWrite {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 文件读写操作演示 ===");
        System.out.println("1. 写入文件");
        System.out.println("2. 读取文件");
        System.out.println("3. 追加内容到文件");
        System.out.print("请选择操作: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 清除换行符

        String fileName = "demo.txt";

        try {
            switch (choice) {
                case 1:
                    writeToFile(fileName, scanner);
                    break;
                case 2:
                    readFromFile(fileName);
                    break;
                case 3:
                    appendToFile(fileName, scanner);
                    break;
                default:
                    System.out.println("无效的选择");
            }
        } catch (IOException e) {
            System.out.println("文件操作错误: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // 写入文件
    private static void writeToFile(String fileName, Scanner scanner) throws IOException {
        System.out.println("请输入要写入文件的内容（输入'END'结束）:");

        StringBuilder content = new StringBuilder();
        String line;

        while (true) {
            line = scanner.nextLine();
            if (line.equals("END")) {
                break;
            }
            content.append(line).append("\n");
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content.toString());
            System.out.println("内容已成功写入文件: " + fileName);
        }
    }

    // 读取文件
    private static void readFromFile(String fileName) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("文件不存在: " + fileName);
            return;
        }

        System.out.println("=== 文件内容 ===");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }
        }

        // 显示文件信息
        System.out.println("\n=== 文件信息 ===");
        System.out.println("文件大小: " + file.length() + " 字节");
        System.out.println("最后修改时间: " + new java.util.Date(file.lastModified()));
    }

    // 追加内容到文件
    private static void appendToFile(String fileName, Scanner scanner) throws IOException {
        System.out.println("请输入要追加的内容（输入'END'结束）:");

        StringBuilder content = new StringBuilder();
        String line;

        while (true) {
            line = scanner.nextLine();
            if (line.equals("END")) {
                break;
            }
            content.append(line).append("\n");
        }

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(content.toString());
            System.out.println("内容已成功追加到文件: " + fileName);
        }
    }
}
