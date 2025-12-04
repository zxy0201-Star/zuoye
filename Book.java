package zuoye;

import java.io.*;
import java.util.*;

class Book {
    private String isbn;
    private String title;
    private String author;
    private int stock;

    public Book(String isbn, String title, String author, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("ISBN: %s, 书名: %s, 作者: %s, 库存: %d", isbn, title, author, stock);
    }
}

 class LibraryManagementSystem {
    private static final String FILE_NAME = "books.txt";
    private static List<Book> books = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 从文件加载图书信息
        loadBooksFromFile();

        while (true) {
            System.out.println("\n=== 图书管理系统 ===");
            System.out.println("1. 添加图书");
            System.out.println("2. 查看所有图书");
            System.out.println("3. 借书");
            System.out.println("4. 还书");
            System.out.println("5. 保存图书信息到文件");
            System.out.println("6. 退出");
            System.out.print("请选择操作: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayAllBooks();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    saveBooksToFile();
                    break;
                case 6:
                    System.out.println("感谢使用图书管理系统!");
                    saveBooksToFile(); // 退出前保存
                    scanner.close();
                    return;
                default:
                    System.out.println("无效的选择，请重新输入!");
            }
        }
    }

    private static void addBook() {
        System.out.print("请输入ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("请输入书名: ");
        String title = scanner.nextLine();

        System.out.print("请输入作者: ");
        String author = scanner.nextLine();

        System.out.print("请输入库存数量: ");
        int stock = scanner.nextInt();

        books.add(new Book(isbn, title, author, stock));
        System.out.println("图书添加成功!");
    }

    private static void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("暂无图书信息");
            return;
        }

        // 使用Comparator对图书按ISBN编号排序
        books.sort(Comparator.comparing(Book::getIsbn));

        System.out.println("\n=== 所有图书列表 ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static void borrowBook() {
        System.out.print("请输入要借阅的图书ISBN: ");
        String isbn = scanner.nextLine();

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                if (book.getStock() > 0) {
                    book.setStock(book.getStock() - 1);
                    System.out.println("借书成功! 剩余库存: " + book.getStock());
                } else {
                    System.out.println("库存不足，无法借阅!");
                }
                return;
            }
        }

        System.out.println("未找到ISBN为 " + isbn + " 的图书");
    }

    private static void returnBook() {
        System.out.print("请输入要归还的图书ISBN: ");
        String isbn = scanner.nextLine();

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setStock(book.getStock() + 1);
                System.out.println("还书成功! 当前库存: " + book.getStock());
                return;
            }
        }

        System.out.println("未找到ISBN为 " + isbn + " 的图书");
    }

    private static void saveBooksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.println(book.getIsbn() + "," + book.getTitle() + "," +
                        book.getAuthor() + "," + book.getStock());
            }
            System.out.println("图书信息已保存到文件: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("保存文件失败: " + e.getMessage());
        }
    }

    private static void loadBooksFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("文件不存在，将创建新文件");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String isbn = parts[0];
                    String title = parts[1];
                    String author = parts[2];
                    int stock = Integer.parseInt(parts[3]);
                    books.add(new Book(isbn, title, author, stock));
                }
            }
            System.out.println("从文件加载了 " + books.size() + " 本图书信息");
        } catch (IOException e) {
            System.out.println("读取文件失败: " + e.getMessage());
        }
    }
}