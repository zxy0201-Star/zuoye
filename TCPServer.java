package zuoye;

import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            // 创建服务器Socket，监听端口8888
            serverSocket = new ServerSocket(8888);
            System.out.println("服务器启动，等待客户端连接...");

            while (true) {
                // 等待客户端连接
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端连接成功，客户端地址：" + clientSocket.getInetAddress());

                // 使用新线程处理客户端请求
                new ClientHandler(clientSocket).start();
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

    // 客户端处理器线程类
    static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            // 使用try-with-resources确保资源正确关闭
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                // 读取客户端消息
                String message = in.readLine();
                if (message == null) {
                    message = "";
                }
                System.out.println("收到客户端消息: " + message);

                // 向客户端发送响应
                String response = "服务器已收到消息: " + message;
                out.println(response);
                System.out.println("已向客户端发送响应: " + response);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
