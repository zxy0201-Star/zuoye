package zuoye;

import java.util.*;

class Person1 {
    private final String name;
    private final int age;

    public Person1(String name, int age) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age must be non-negative");
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

// 按年龄升序比较器 - 枚举单例模式
enum ageComparator implements Comparator<Person> {
    INSTANCE;

    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }
}

// 按姓名升序比较器 - 枚举单例模式，并防止 NullPointerException 和 空字符串混乱排序
enum nameComparator implements Comparator<Person> {
    INSTANCE;

    @Override
    public int compare(Person p1, Person p2) {
        String n1 = p1.getName();
        String n2 = p2.getName();
        if (n1 == null && n2 == null) return 0;
        if (n1 == null) return -1;
        if (n2 == null) return 1;
        return n1.compareTo(n2);
    }
}

 class ComparatorDemo {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 25));
        people.add(new Person("Bob", 30));
        people.add(new Person("Charlie", 22));
        people.add(new Person("David", 28));

        printList("原始列表:", people);

        // 使用AgeComparator按年龄排序
        List<Person> sortedByAgeAsc = new ArrayList<>(people);
        sortedByAgeAsc.sort(ageComparator.INSTANCE);
        printList("\n按年龄升序排序:", sortedByAgeAsc);

        // 使用NameComparator按姓名排序
        List<Person> sortedByNameAsc = new ArrayList<>(people);
        sortedByNameAsc.sort(nameComparator.INSTANCE);
        printList("\n按姓名升序排序:", sortedByNameAsc);

        // 使用Lambda表达式按年龄降序排序
        List<Person> sortedByAgeDesc = new ArrayList<>(people);
        sortedByAgeDesc.sort(Comparator.comparingInt(Person::getAge).reversed());
        printList("\n按年龄降序排序（使用Lambda）:", sortedByAgeDesc);
    }

    private static void printList(String title, List<Person> list) {
        System.out.println(title);
        System.out.println(list);
    }
}
