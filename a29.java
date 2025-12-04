package zuoye;

import java.util.ArrayList;
import java.util.List;

public class a29 {

    public interface Shape {
        double calculateArea();       // 计算面积
        double calculatePerimeter();  // 计算周长
        String getShapeName();        // 获取图形名称
    }

    public static class Circle implements Shape {
        private final double radius;

        public Circle(double radius) {
            if (radius < 0) throw new IllegalArgumentException("半径不能为负");
            this.radius = radius;
        }

        @Override
        public double calculateArea() {
            return Math.PI * radius * radius;
        }

        @Override
        public double calculatePerimeter() {
            return 2 * Math.PI * radius;
        }

        @Override
        public String getShapeName() {
            return "圆形";
        }

        public double getRadius() {
            return radius;
        }
    }

    public static class Rectangle implements Shape {
        private final double width;
        private final double height;

        public Rectangle(double width, double height) {
            if (width < 0 || height < 0)
                throw new IllegalArgumentException("宽度和高度不能为负");
            this.width = width;
            this.height = height;
        }

        @Override
        public double calculateArea() {
            return width * height;
        }

        @Override
        public double calculatePerimeter() {
            return 2 * (width + height);
        }

        @Override
        public String getShapeName() {
            return "矩形";
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }

    public static class Triangle implements Shape {
        private final double sideA;
        private final double sideB;
        private final double sideC;

        public Triangle(double sideA, double sideB, double sideC) {
            if (sideA <= 0 || sideB <= 0 || sideC <= 0)
                throw new IllegalArgumentException("三角形的边长必须大于0");

            if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA)
                throw new IllegalArgumentException("输入的三边无法构成有效三角形");

            this.sideA = sideA;
            this.sideB = sideB;
            this.sideC = sideC;
        }

        @Override
        public double calculateArea() {
            // 使用海伦公式计算三角形面积
            double s = (sideA + sideB + sideC) / 2;
            return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
        }

        @Override
        public double calculatePerimeter() {
            return sideA + sideB + sideC;
        }

        @Override
        public String getShapeName() {
            return "三角形";
        }

        public double getSideA() {
            return sideA;
        }

        public double getSideB() {
            return sideB;
        }

        public double getSideC() {
            return sideC;
        }
    }

    public static void printShapeInfo(Shape shape) {
        System.out.println("图形: " + shape.getShapeName());
        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            System.out.println("半径: " + c.getRadius());
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            System.out.println("宽度: " + r.getWidth());
            System.out.println("高度: " + r.getHeight());
        } else if (shape instanceof Triangle) {
            Triangle t = (Triangle) shape;
            System.out.println("边A: " + t.getSideA());
            System.out.println("边B: " + t.getSideB());
            System.out.println("边C: " + t.getSideC());
        }
        System.out.printf("面积: %.2f\n", shape.calculateArea());
        System.out.printf("周长: %.2f\n", shape.calculatePerimeter());
    }

    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();

        try {
            shapes.add(new Circle(5));
            shapes.add(new Rectangle(4, 6));
            shapes.add(new Triangle(3, 4, 5));
        } catch (IllegalArgumentException e) {
            System.err.println("创建图形失败：" + e.getMessage());
            return;
        }

        double totalArea = 0;
        double totalPerimeter = 0;

        for (Shape shape : shapes) {
            printShapeInfo(shape);
            System.out.println();

            totalArea += shape.calculateArea();
            totalPerimeter += shape.calculatePerimeter();
        }

        System.out.printf("所有图形的总面积: %.2f\n", totalArea);
        System.out.printf("所有图形的总周长: %.2f\n", totalPerimeter);
    }
}
