package zuoye;
import java.util.ArrayList;
import java.util.Scanner;
public class a8 {  private int id;
    private String name;
    private double price;
    private int stock;

    public  a8(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void displayInfo() {
        System.out.printf("ID: %d | 商品名: %-15s | 价格: ¥%.2f | 库存: %d\n",
                id, name, price, stock);
    }
}

 class ProductManagementSystem {
    private static ArrayList<a8> productList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1;

    public static void main(String[] args) {
        // 初始化一些示例商品
        productList.add(new a8(nextId++, "苹果手机", 6999.0, 50));
        productList.add(new a8(nextId++, "华为笔记本", 5999.0, 30));
        productList.add(new a8(nextId++, "小米电视", 3299.0, 25));

        while (true) {
            System.out.println("\n========== 商品管理系统 ==========");
            System.out.println("1. 添加商品");
            System.out.println("2. 查看所有商品");
            System.out.println("3. 根据ID查询商品");
            System.out.println("4. 退出系统");
            System.out.print("请选择操作: ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("输入错误，请输入数字!");
                scanner.nextLine(); // 清除无效输入
                continue;
            }

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayAllProducts();
                    break;
                case 3:
                    searchProductById();
                    break;
                case 4:
                    System.out.println("感谢使用商品管理系统，再见!");
                    scanner.close();
                    return;
                default:
                    System.out.println("无效的选择，请重新输入!");
            }
        }
    }

    private static void addProduct() {
        System.out.print("请输入商品名称: ");
        scanner.nextLine(); // 清除换行符
        String name = scanner.nextLine();

        System.out.print("请输入商品价格: ");
        double price = scanner.nextDouble();

        System.out.print("请输入商品库存: ");
        int stock = scanner.nextInt();

        a8 product = new a8(nextId++, name, price, stock);
        productList.add(product);

        System.out.println("商品添加成功!");
        product.displayInfo();
    }

    private static void displayAllProducts() {
        if (productList.isEmpty()) {
            System.out.println("暂无商品信息");
            return;
        }

        System.out.println("\n========== 所有商品列表 ==========");
        for (a8 product : productList) {
            product.displayInfo();
        }
        System.out.println("=================================");
    }

    private static void searchProductById() {
        System.out.print("请输入商品ID: ");
        int id = scanner.nextInt();

        for (a8 product : productList) {
            if (product.getId() == id) {
                System.out.println("\n查询结果:");
                product.displayInfo();
                return;
            }
        }

        System.out.println("未找到ID为 " + id + " 的商品");
    }
}

