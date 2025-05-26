package cn.personalstudy.base.socket;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author: qiaohaojie
 * @date: 2025-05-26 22:15:44
 */
public class TCPServer {

    /**
     * Java网络编程中的几个基本概念
     * 1. IP地址：用于标识网络中的计算机
     * 2. 端口号：用于标识计算机上的具体应用程序或进程
     * 3. Socket（套接字）：网络通信的基本的单位，通过IP地址和端口号标识
     * 4. 通信协议：网络通信的规则，例如：TCP（传输控制协议）和UDP（用户数据报协议）
     * <p>
     * 举例：
     * 1. IP地址：外卖员要送餐到的大楼
     * 2. 端口号：大楼里具体的房间
     * 3. Socket：外卖员通过手机与我们通信，告知外卖已到
     * 4. 通信协议：默认使用中文对话
     *
     * @param args
     */
    public static void main(String[] args) {
        // 服务端创建ServerSocket监听端口8888
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("服务器启动成功");

            while (true) {
                // 阻塞等待客户端连接
                Socket socket = serverSocket.accept();
                // 客户端创建Socket发起连接
                new ServerThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
