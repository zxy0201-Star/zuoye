package zuoye;

import java.util.Scanner;

public class EnumUserRole {

    // 定义用户角色枚举
    public enum UserRole {
        // 枚举常量，可以带有属性
        ADMIN("管理员", 1, new String[]{"所有权限", "用户管理", "系统配置"}),
        USER("普通用户", 2, new String[]{"查看数据", "修改个人资料", "提交申请"}),
        GUEST("访客", 3, new String[]{"查看公开信息", "浏览网站"}),
        MODERATOR("版主", 4, new String[]{"内容管理", "用户举报处理", "置顶帖子"});

        // 枚举属性
        private final String roleName;
        private final int roleLevel;
        private final String[] permissions;

        // 枚举构造函数（默认为private）
        UserRole(String roleName, int roleLevel, String[] permissions) {
            this.roleName = roleName;
            this.roleLevel = roleLevel;
            this.permissions = permissions;
        }

        // 获取角色名称
        public String getRoleName() {
            return roleName;
        }

        // 获取角色级别（数字越小权限越大）
        public int getRoleLevel() {
            return roleLevel;
        }

        // 获取权限列表
        public String[] getPermissions() {
            return permissions;
        }

        // 检查是否拥有某项权限
        public boolean hasPermission(String permission) {
            for (String p : permissions) {
                if (p.equals(permission)) {
                    return true;
                }
            }
            return false;
        }

        // 显示角色信息
        public void displayRoleInfo() {
            System.out.println("角色: " + roleName);
            System.out.println("级别: " + roleLevel);
            System.out.println("权限列表:");
            for (int i = 0; i < permissions.length; i++) {
                System.out.println("  " + (i + 1) + ". " + permissions[i]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 用户权限管理系统 ===");

        // 1. 遍历所有角色
        System.out.println("\n=== 所有用户角色 ===");
        for (UserRole role : UserRole.values()) {
            System.out.println(role.name() + " - " + role.getRoleName() + " (级别: " + role.getRoleLevel() + ")");
        }

        // 2. 根据用户输入显示角色信息
        System.out.println("\n=== 查看角色详情 ===");
        System.out.print("请输入角色名称(ADMIN/USER/GUEST/MODERATOR): ");
        String roleInput = scanner.nextLine().toUpperCase();

        try {
            UserRole selectedRole = UserRole.valueOf(roleInput);
            System.out.println("\n角色详情:");
            selectedRole.displayRoleInfo();

            // 3. 权限检查示例
            System.out.println("=== 权限检查 ===");
            System.out.print("请输入要检查的权限: ");
            String permissionToCheck = scanner.nextLine();

            if (selectedRole.hasPermission(permissionToCheck)) {
                System.out.println(selectedRole.getRoleName() + " 拥有权限: " + permissionToCheck);
            } else {
                System.out.println(selectedRole.getRoleName() + " 没有权限: " + permissionToCheck);
            }

            // 4. 角色比较
            System.out.println("\n=== 角色比较 ===");
            System.out.println("比较 " + UserRole.ADMIN.getRoleName() + " 和 " + UserRole.USER.getRoleName());

            if (UserRole.ADMIN.getRoleLevel() < UserRole.USER.getRoleLevel()) {
                System.out.println(UserRole.ADMIN.getRoleName() + " 权限高于 " + UserRole.USER.getRoleName());
            }

            // 5. 使用switch语句处理不同角色
            System.out.println("\n=== 使用switch处理角色 ===");
            handleRole(selectedRole);

        } catch (IllegalArgumentException e) {
            System.out.println("错误: 无效的角色名称!");
        }

        // 6. 高级用法：查找具有特定权限的角色
        System.out.println("\n=== 查找具有特定权限的角色 ===");
        System.out.print("请输入要查找的权限: ");
        String searchPermission = scanner.nextLine();

        System.out.println("拥有权限 '" + searchPermission + "' 的角色:");
        for (UserRole role : UserRole.values()) {
            if (role.hasPermission(searchPermission)) {
                System.out.println("  - " + role.getRoleName());
            }
        }

        scanner.close();
    }

    // 使用switch语句处理不同角色
    public static void handleRole(UserRole role) {
        switch (role) {
            case ADMIN:
                System.out.println("欢迎管理员！您可以进行所有操作。");
                System.out.println("系统管理选项:");
                System.out.println("  1. 用户管理");
                System.out.println("  2. 系统配置");
                System.out.println("  3. 日志查看");
                break;

            case USER:
                System.out.println("欢迎用户！您可以进行常规操作。");
                System.out.println("用户功能:");
                System.out.println("  1. 查看个人资料");
                System.out.println("  2. 修改密码");
                System.out.println("  3. 提交申请");
                break;

            case GUEST:
                System.out.println("欢迎访客！您可以浏览公开信息。");
                System.out.println("访客功能:");
                System.out.println("  1. 浏览网站");
                System.out.println("  2. 查看公开信息");
                System.out.println("  3. 注册账号");
                break;

            case MODERATOR:
                System.out.println("欢迎版主！您可以管理内容。");
                System.out.println("版主功能:");
                System.out.println("  1. 内容审核");
                System.out.println("  2. 用户举报处理");
                System.out.println("  3. 帖子管理");
                break;
        }
    }
}