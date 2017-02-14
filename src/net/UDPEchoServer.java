package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 它不停地循环，接收数据报文后将相同的数据报文返回给客户端。实际上我们的服务器只接收和发送数据报文中的前
 * 255（ECHOMAX）个字符，超出的部分将在套接字的具体实现中无提示地丢弃。
 */
public class UDPEchoServer {

    private static final int ECHOMAX = 255; // Maximum size of echo datagram
    private static final int SERVER_PORT = 9092;

    public static void main(String[] args) throws IOException {
        int servPort = SERVER_PORT;
        DatagramSocket socket = new DatagramSocket(servPort);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

        while (true) { // Run forever, receiving and echoing datagrams
            socket.receive(packet); // Receive packet from client
            System.out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
            socket.send(packet); // Send the same packet back to client
            packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
        }
        /* NOT REACHED */
    }
}
