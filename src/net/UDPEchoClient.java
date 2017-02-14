package net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPEchoClient {

    private static final int TIMEOUT = 3000; // 超时时间
    private static final int MAXTRIES = 5; // 超时前若没收到响应最多重发5次

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9092;

    public static void main(String[] args) throws IOException {
        String msg = "UDP client test! 15464444444444566666666656456465456456456456456465";
        byte[] bytesToSend = msg.getBytes();
        InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
        int servPort = SERVER_PORT;
        
        DatagramSocket socket = new DatagramSocket(); // 创建UDP套接字
        // 数据报文套接字的超时时间，用来控制调用 receive()方法的最长阻塞时间（毫秒）
        socket.setSoTimeout(TIMEOUT);
        // 创建一个要发送的数据报文
        DatagramPacket sendPacket = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, servPort);
        //创建一个要接收的数据报文， 我们只需要定义一个用来存放报文数据的字节数组。 而数据报文的源地址和端口号将从 receive()方法获得。
        DatagramPacket receivePacket = new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);

        int tries = 0; // 如果包丢失了，可以进行重复发送
        boolean receivedResponse = false;
        do {
            socket.send(sendPacket); // send()方法将数据报文传输到其指定的地址和端口号去。
            try {
                //receive()方法将阻塞等待，直到收到一个数据报文或等待超时。超时信息由InterruptedIOException 异常指示。
                socket.receive(receivePacket);
                if (!receivePacket.getAddress().equals(serverAddress)) {
                    throw new IOException("Received packet from an unknown source");
                }
                receivedResponse = true;
            } catch (InterruptedIOException e) {
                e.printStackTrace();
            }
            tries += 1;
            System.out.println("Timed out, " + (MAXTRIES - tries) + " more tries...");
        } while ((!receivedResponse) && (tries < MAXTRIES));
        if (receivedResponse) {
            System.out.println("Received: " + new String(receivePacket.getData()));
        } else {
            System.out.println("No response -- giving up.");
        }
        socket.close();
    }
}
