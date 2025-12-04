package zuoye;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Employee {
    private String employeeId;
    private String name;
    private double baseSalary;

    public Employee(String employeeId, String name, double baseSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // 抽象方法：计算实际工资
    public abstract double calculateSalary();

    // 具体方法：获取员工信息
    public String getEmployeeInfo() {
        return String.format("ID: %s, 姓名: %s, 基础工资: %.2f, 实际工资: %.2f",
                employeeId, name, baseSalary, calculateSalary());
    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}

class FullTimeEmployee extends Employee {
    private double performanceBonus;

    public FullTimeEmployee(String employeeId, String name, double baseSalary, double performanceBonus) {
        super(employeeId, name, baseSalary);
        this.performanceBonus = performanceBonus;
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + performanceBonus;
    }

    public static String getDisplayTypeName() {
        return "全职员工";
    }
}

class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String employeeId, String name, double baseSalary, double hourlyRate, int hoursWorked) {
        super(employeeId, name, baseSalary);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + hourlyRate * hoursWorked;
    }

    public static String getDisplayTypeName() {
        return "兼职员工";
    }
}

class SalesEmployee extends Employee {
    private double salesAmount;
    private static final double COMMISSION_RATE = 0.05; // 5%提成

    public SalesEmployee(String employeeId, String name, double baseSalary, double salesAmount) {
        super(employeeId, name, baseSalary);
        this.salesAmount = salesAmount;
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + (salesAmount * COMMISSION_RATE);
    }

    public static String getDisplayTypeName() {
        return "销售员工";
    }
}

 class Company {
    private List<Employee> employees = new ArrayList<>();

    // 添加员工
    public void addEmployee(Employee employee) {
        if (employee == null) {
            System.out.println("无法添加空员工");
            return;
        }
        if (employees.contains(employee)) {
            System.out.println("员工ID已存在：" + employee.getEmployeeId());
            return;
        }
        employees.add(employee);
        System.out.println("添加员工: " + employee.getEmployeeInfo());
    }

    // 删除员工
    public void removeEmployee(String employeeId) {
        if (employeeId == null) {
            System.out.println("员工ID不能为空");
            return;
        }

        boolean removed = employees.removeIf(emp -> emp.getEmployeeId().equals(employeeId));
        if (removed) {
            System.out.println("删除员工ID: " + employeeId);
        } else {
            System.out.println("未找到员工ID: " + employeeId);
        }
    }

    // 计算所有员工的总工资
    public double calculateTotalSalary() {
        double total = 0;
        for (Employee emp : employees) {
            total += emp.calculateSalary();
        }
        return total;
    }

    // 按工资从低到高排序显示所有员工信息
    public void displayEmployeesSortedBySalary() {
        employees.sort(Comparator.comparingDouble(Employee::calculateSalary));

        System.out.println("\n=== 员工列表（按工资升序） ===");
        for (Employee emp : employees) {
            System.out.println(emp.getEmployeeInfo());
        }
    }

    // 找出工资最高和最低的员工
    public void findMinMaxSalaryEmployees() {
        if (employees.isEmpty()) {
            System.out.println("没有员工记录");
            return;
        }

        Employee minSalaryEmployee = employees.get(0);
        Employee maxSalaryEmployee = employees.get(0);
        double minSalary = minSalaryEmployee.calculateSalary();
        double maxSalary = maxSalaryEmployee.calculateSalary();

        for (int i = 1; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            double salary = emp.calculateSalary();
            if (salary < minSalary) {
                minSalaryEmployee = emp;
                minSalary = salary;
            }
            if (salary > maxSalary) {
                maxSalaryEmployee = emp;
                maxSalary = salary;
            }
        }

        System.out.println("\n工资最低的员工: " + minSalaryEmployee.getEmployeeInfo());
        System.out.println("工资最高的员工: " + maxSalaryEmployee.getEmployeeInfo());
    }

    // 使用Stream API进行数据统计
    public void displaySalaryStatistics() {
        if (employees.isEmpty()) {
            System.out.println("没有员工记录");
            return;
        }

        System.out.println("\n=== 工资统计 ===");

        DoubleSummaryStatistics salaryStats = employees.stream()
                .mapToDouble(Employee::calculateSalary)
                .summaryStatistics();

        Map<String, Long> typeCounts = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> {
                            if (emp instanceof FullTimeEmployee) return FullTimeEmployee.getDisplayTypeName();
                            if (emp instanceof PartTimeEmployee) return PartTimeEmployee.getDisplayTypeName();
                            if (emp instanceof SalesEmployee) return SalesEmployee.getDisplayTypeName();
                            return "未知类型";
                        },
                        Collectors.counting()
                ));

        System.out.println("平均工资: " + salaryStats.getAverage());
        typeCounts.forEach((type, count) ->
                System.out.println(type + "人数: " + count));
    }

    public static void main(String[] args) {
        Company company = new Company();
        initTestData(company);

        // 计算总工资
        System.out.println("\n公司总工资支出: " + company.calculateTotalSalary());

        // 按工资排序显示员工
        company.displayEmployeesSortedBySalary();

        // 找出工资最高和最低的员工
        company.findMinMaxSalaryEmployees();

        // 显示统计信息
        company.displaySalaryStatistics();
    }

    private static void initTestData(Company company) {
        company.addEmployee(new FullTimeEmployee("001", "张三", 8000, 2000));
        company.addEmployee(new PartTimeEmployee("002", "李四", 0, 50, 80));
        company.addEmployee(new SalesEmployee("003", "王五", 5000, 100000));
        company.addEmployee(new FullTimeEmployee("004", "赵六", 7000, 1500));
    }
}
