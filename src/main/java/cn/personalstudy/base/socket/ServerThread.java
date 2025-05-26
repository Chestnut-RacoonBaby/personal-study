package cn.personalstudy.base.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端线程
 *
 * @author: qiaohaojie
 * @date: 2025-05-26 22:20:50
 */
public class ServerThread extends Thread {

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 创建一个字符流输出对象，用于向客户端发送数据。且每次调用println()方法后会自动刷新缓冲区
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // 创建一个字符输入对象，用于从客户端接收数据
            // 使用InputStreamReader将字节流转化为字符流，再通过缓冲流提高读取性能
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 读取客户端信息
            String request = in.readLine();
            System.out.println("收到客户端请求：" + request);

            // 响应客户端
            out.println("响应客户端：" + request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
