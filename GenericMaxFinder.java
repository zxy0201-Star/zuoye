package zuoye;

import java.util.Arrays;

public class GenericMaxFinder {

    // 泛型方法：查找数组中的最大值
    // 要求类型T实现Comparable接口，这样可以使用compareTo方法比较大小
    public static <T extends Comparable<T>> T findMax(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    // 重载方法：可以指定Comparator
    public static <T> T findMax(T[] array, java.util.Comparator<T> comparator) {
        if (array == null || array.length == 0 || comparator == null) {
            return null;
        }

        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (comparator.compare(array[i], max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println("=== 查找最大值的泛型方法演示 ===");

        // 测试1：整数数组
        Integer[] intArray = {3, 7, 2, 9, 5, 1, 8};
        System.out.println("整数数组: " + Arrays.toString(intArray));
        Integer intMax = findMax(intArray);
        System.out.println("最大值: " + intMax);

        // 测试2：双精度浮点数数组
        Double[] doubleArray = {3.14, 2.71, 1.618, 9.8, 0.577};
        System.out.println("\n双精度数组: " + Arrays.toString(doubleArray));
        Double doubleMax = findMax(doubleArray);
        System.out.println("最大值: " + doubleMax);

        // 测试3：字符串数组（按字典顺序）
        String[] stringArray = {"apple", "banana", "orange", "grape", "peach"};
        System.out.println("\n字符串数组: " + Arrays.toString(stringArray));
        String stringMax = findMax(stringArray);
        System.out.println("最大值（按字典顺序）: " + stringMax);

        // 测试4：自定义对象数组
        Person[] personArray = {
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 22),
                new Person("David", 28)
        };

        System.out.println("\n自定义对象数组（Person）:");
        for (Person p : personArray) {
            System.out.println("  " + p);
        }

        // 按年龄查找最大值
        Person personByAge = findMax(personArray, new AgeComparator());
        System.out.println("年龄最大的人: " + personByAge);

        // 按名字查找最大值（按字典顺序）
        Person personByName = findMax(personArray, new NameComparator());
        System.out.println("名字最大的人（按字典顺序）: " + personByName);

        // 测试5：空数组和null处理
        Integer[] emptyArray = {};
        Integer[] nullArray = null;

        System.out.println("\n=== 边界情况测试 ===");
        System.out.println("空数组的最大值: " + findMax(emptyArray));
        System.out.println("null数组的最大值: " + findMax(nullArray));
    }
}

// 自定义Person类
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
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
        return name + " (" + age + "岁)";
    }
}

// 按年龄比较的Comparator
class AgeComparator implements java.util.Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }
}

// 按名字比较的Comparator
class NameComparator implements java.util.Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().compareTo(p2.getName());
    }
}