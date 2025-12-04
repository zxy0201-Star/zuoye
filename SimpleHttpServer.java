package zuoye;

import java.io.*;
import java.net.*;

public class SimpleHttpServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            // 创建服务器Socket，监听端口8080
            serverSocket = new ServerSocket(8080);
            System.out.println("HTTP服务器启动，监听端口 8080");

            while (true) {
                // 等待客户端连接
                Socket clientSocket = serverSocket.accept();
                System.out.println("接收到客户端连接: " + clientSocket.getInetAddress());

                // 处理客户端请求
                handleRequest(clientSocket);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // 读取请求的第一行
        String requestLine = in.readLine();
        System.out.println("请求: " + requestLine);

        // 解析请求方法
        if (requestLine != null && requestLine.startsWith("GET")) {
            // 构建响应
            String responseBody = "<html><body><h1>Hello, World!</h1><p>这是一个简单的HTTP服务器。</p></body></html>";

            // 发送HTTP响应头
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("Content-Length: " + responseBody.getBytes().length);
            out.println();

            // 发送响应体
            out.println(responseBody);
        } else {
            // 不支持的请求方法
            out.println("HTTP/1.1 405 Method Not Allowed");
            out.println();
        }

        in.close();
        out.close();
    }
}