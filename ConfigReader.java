package zuoye;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    // 定义配置项常量
    private static final String DB_HOST_KEY = "db.host";
    private static final String DB_PORT_KEY = "db.port";
    private static final String DB_USERNAME_KEY = "db.username";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String APP_NAME_KEY = "app.name";
    private static final String APP_VERSION_KEY = "app.version";
    private static final String TIMEOUT_KEY = "timeout";

    public static void main(String[] args) {
        Properties props = new Properties();
        String configPath = getConfigFilePath(args); // 支持从参数传入路径

        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);

            // 获取并验证必要配置项
            String dbHost = getRequiredProperty(props, DB_HOST_KEY);
            String dbPort = getRequiredProperty(props, DB_PORT_KEY);
            String dbUsername = getRequiredProperty(props, DB_USERNAME_KEY);
            String dbPassword = getRequiredProperty(props, DB_PASSWORD_KEY);
            String appName = getRequiredProperty(props, APP_NAME_KEY);
            String appVersion = getRequiredProperty(props, APP_VERSION_KEY);

            // 输出配置信息
            System.out.println("数据库配置:");
            System.out.println("  主机: " + dbHost);
            System.out.println("  端口: " + dbPort);
            System.out.println("  用户名: " + dbUsername);
            System.out.println("  密码: " + dbPassword);
            System.out.println("应用配置:");
            System.out.println("  应用名称: " + appName);
            System.out.println("  版本: " + appVersion);

            // 设置默认值
            String timeout = props.getProperty(TIMEOUT_KEY, "30");
            System.out.println("超时时间(默认值): " + timeout);

        } catch (IOException e) {
            System.err.println("读取配置文件失败: " + e.getMessage());
            e.printStackTrace(); // 更详细的堆栈跟踪用于调试
        }
    }

    /**
     * 从命令行参数中获取配置文件路径，默认为 config.properties
     */
    private static String getConfigFilePath(String[] args) {
        if (args.length > 0 && args[0] != null && !args[0].isEmpty()) {
            return args[0];
        }
        return "config.properties";
    }

    /**
     * 获取必需的配置项，若为空则抛出异常
     */
    private static String getRequiredProperty(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing required configuration property: " + key);
        }
        return value;
    }
}
