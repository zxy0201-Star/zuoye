package zuoye;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Scanner;

public class ReflectionClassInfo {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== 反射获取类信息演示 ===");

            System.out.print("请输入要分析的完整类名（例如：java.util.ArrayList）: ");
            String className = scanner.nextLine();

            try {
                // 加载类
                Class<?> clazz = Class.forName(className);

                // 显示类基本信息
                displayClassBasicInfo(clazz);

                // 显示构造方法信息
                displayConstructors(clazz);

                // 显示方法信息
                displayMethods(clazz);

                // 显示字段信息
                displayFields(clazz);

                // 显示继承关系
                displayInheritance(clazz);

                // 显示实现的接口
                displayInterfaces(clazz);

                // 显示注解信息
                displayAnnotations(clazz);

                // 创建实例并调用方法（示例）
                createInstanceAndInvokeMethod(clazz, scanner);

            } catch (ClassNotFoundException e) {
                System.out.println("错误: 未找到类 '" + className + "'");
                System.out.println("提示: 请确保输入完整的类名，包括包名");

                // 尝试使用内置类
                System.out.println("\n尝试使用java.lang.String类作为示例...");
                try {
                    displayClassBasicInfo(String.class);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println("错误: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 显示类基本信息
    private static void displayClassBasicInfo(Class<?> clazz) {
        if (clazz == null) return;

        System.out.println("\n=== 类基本信息 ===");
        System.out.println("类名: " + clazz.getName());
        System.out.println("简单类名: " + clazz.getSimpleName());
        System.out.println("规范类名: " + clazz.getCanonicalName());
        System.out.println("是否为接口: " + clazz.isInterface());
        System.out.println("是否为数组: " + clazz.isArray());
        System.out.println("是否为基本类型: " + clazz.isPrimitive());
        System.out.println("是否为枚举: " + clazz.isEnum());
        System.out.println("是否为注解: " + clazz.isAnnotation());
        System.out.println("访问修饰符: " + Modifier.toString(clazz.getModifiers()));
        System.out.println("类加载器: " + clazz.getClassLoader());
    }

    // 显示构造方法信息
    private static void displayConstructors(Class<?> clazz) {
        System.out.println("\n=== 构造方法 ===");
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length == 0) {
            System.out.println("没有构造方法");
        } else {
            for (Constructor<?> constructor : constructors) {
                System.out.println("  " + Modifier.toString(constructor.getModifiers()) + " " +
                        clazz.getSimpleName() + getParameterTypes(constructor.getParameterTypes()));
            }
        }
    }

    // 显示方法信息
    private static void displayMethods(Class<?> clazz) {
        System.out.println("\n=== 方法列表 ===");
        Method[] methods = clazz.getDeclaredMethods();

        if (methods.length == 0) {
            System.out.println("没有方法");
        } else {
            for (Method method : methods) {
                System.out.println("  " + Modifier.toString(method.getModifiers()) + " " +
                        method.getReturnType().getSimpleName() + " " +
                        method.getName() + getParameterTypes(method.getParameterTypes()));

                // 显示异常信息
                Class<?>[] exceptions = method.getExceptionTypes();
                if (exceptions.length > 0) {
                    System.out.print("    throws ");
                    for (int i = 0; i < exceptions.length; i++) {
                        System.out.print(exceptions[i].getSimpleName());
                        if (i < exceptions.length - 1) System.out.print(", ");
                    }
                    System.out.println();
                }
            }
        }
    }

    // 显示字段信息
    private static void displayFields(Class<?> clazz) {
        System.out.println("\n=== 字段列表 ===");
        Field[] fields = clazz.getDeclaredFields();

        if (fields.length == 0) {
            System.out.println("没有字段");
        } else {
            for (Field field : fields) {
                System.out.println("  " + Modifier.toString(field.getModifiers()) + " " +
                        field.getType().getSimpleName() + " " + field.getName());
            }
        }
    }

    // 显示继承关系
    private static void displayInheritance(Class<?> clazz) {
        System.out.println("\n=== 继承关系 ===");

        // 获取父类
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            System.out.println("父类: " + superClass.getName());

            // 显示继承链
            System.out.print("继承链: " + clazz.getSimpleName());
            Class<?> current = superClass;
            while (current != null) {
                System.out.print(" -> " + current.getSimpleName());
                current = current.getSuperclass();
            }
            System.out.println();
        } else {
            System.out.println("没有父类（可能是Object类或接口）");
        }
    }

    // 显示实现的接口
    private static void displayInterfaces(Class<?> clazz) {
        System.out.println("\n=== 实现的接口 ===");
        Class<?>[] interfaces = clazz.getInterfaces();

        if (interfaces.length == 0) {
            System.out.println("没有实现接口");
        } else {
            for (Class<?> iface : interfaces) {
                System.out.println("  " + iface.getName());
            }
        }
    }

    // 显示注解信息
    private static void displayAnnotations(Class<?> clazz) {
        System.out.println("\n=== 注解信息 ===");
        Annotation[] annotations = clazz.getAnnotations();

        if (annotations.length == 0) {
            System.out.println("没有注解");
        } else {
            for (Annotation annotation : annotations) {
                System.out.println("  @" + annotation.annotationType().getSimpleName());
            }
        }
    }

    // 创建实例并调用方法
    private static void createInstanceAndInvokeMethod(Class<?> clazz, Scanner scanner)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        System.out.println("\n=== 创建实例并调用方法 ===");

        // 检查是否有无参构造方法
        Constructor<?> noArgConstructor;
        try {
            noArgConstructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            System.out.println("类没有无参构造方法，跳过实例创建");
            return;
        }

        // 设置为可访问（如果构造方法是private的）
        noArgConstructor.setAccessible(true);

        // 创建实例
        Object instance = noArgConstructor.newInstance();
        System.out.println("成功创建实例: " + instance);

        // 查找并调用toString方法
        try {
            Method toStringMethod = clazz.getMethod("toString");
            Object result = toStringMethod.invoke(instance);
            System.out.println("调用toString()结果: " + result);
        } catch (NoSuchMethodException e) {
            System.out.println("类没有toString方法");
        }

        // 尝试调用其他简单方法
        if (clazz == String.class) {
            // 如果是String类，调用length()方法
            Method lengthMethod = clazz.getMethod("length");
            int length = (int) lengthMethod.invoke(instance);
            System.out.println("字符串长度: " + length);
        }
    }

    // 辅助方法：获取参数类型字符串
    private static String getParameterTypes(Class<?>[] paramTypes) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < paramTypes.length; i++) {
            sb.append(paramTypes[i].getSimpleName());
            if (i < paramTypes.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
