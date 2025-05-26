package cn.personalstudy.base.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 *
 * @author: qiaohaojie
 * @date: 2025-05-26 22:44:54
 */
public class TCPClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8888)) {
            // 创建一个字符流输出对象，用于向服务器发送数据。且每次调用println()方法后会自动刷新缓冲区
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // 创建一个字符输入对象，用于从服务器接收数据
            // 使用InputStreamReader将字节流转化为字符流，再通过缓冲流提高读取性能
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 发送消息给服务器
            out.println("你好，我是客户端");

            // 接收服务器的响应
            String response = in.readLine();
            System.out.println("收到服务器响应：" + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
