package zuoye;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            // 创建客户端Socket，连接服务器
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 8888), 5000); // 设置5秒连接超时

            // 使用try-with-resources确保资源正确关闭
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // 向服务器发送消息
                String message = "Hello, Server!";
                out.println(message);
                System.out.println("已向服务器发送消息: " + message);

                // 接收服务器响应
                String response = in.readLine();
                if (response == null) {
                    response = "";
                }
                System.out.println("收到服务器响应: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
