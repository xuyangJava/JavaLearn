package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 通过 TCP 协议与回馈服务器（echo server）进行通信的客户端
 */
public class TCPEchoClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9091;

    public static void main(String[] args) throws UnknownHostException, IOException {
        testClient("Test client!");
    }
    
    public static void testClient(String msg) throws UnknownHostException, IOException{
        String server = SERVER_IP;
        int servPort = SERVER_PORT;
        //TCP 套接字发送和接收字节序列信息。String 类的 getBytes()方法将返回代表该字符串的一个字节数组。
        byte[] data = msg.getBytes(); // 转换回馈字符串
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server...sending echo string");
        //每个 Socket 实例都关联了一个 InputStream 和一个 OutputStream 对象。
        //通过从 InputStream 读取信息来接受数据
        InputStream in = socket.getInputStream();
        //将字节写入套接字的 OutputStream 来发送数据
        OutputStream out = socket.getOutputStream();
        
        out.write(data); // 传送数据
        
        int totalBytesRcvd = 0; //已经接收了多少字节 
        int bytesRcvd; // 上次读取时接收的字节数
        
        // 为什么不只用一个read方法呢？TCP协议并不能确定在read()和write()方法中所发送信
        // 息的界限，也就是说，虽然我们只用了一个 write()方法来发送回馈字符串，回馈服务器也可
        // 能从多个块（chunks）中接受该信息。即使回馈字符串在服务器上存于一个块中，在返回的
        // 时候，也可能被 TCP 协议分割成多个部分。对于初学者来说，最常见的错误就是认为由一
        // 个 write()方法发送的数据总是会由一个 read()方法来接收。
        while (totalBytesRcvd < data.length) {
            if ((bytesRcvd = in.read(data, totalBytesRcvd, data.length - totalBytesRcvd))  == -1) {
                throw new SocketException("Connection closed prematurely"); // 连接过早关闭
            }
            totalBytesRcvd += bytesRcvd;
            
            System.out.println("Received: " + new String(data));
            socket.close();
        }
    }
}
