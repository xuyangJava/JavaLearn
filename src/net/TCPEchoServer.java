package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPEchoServer {

    private static final int BUFESIZE = 32;
    private static final int PORT = 9091;
    
    public static void main(String[] args) throws IOException {
        int servPort = PORT;
        ServerSocket servSock = new ServerSocket(servPort);
        int recvMsgSize; // Size of received message
        byte[] receiveBuf = new byte[BUFESIZE]; // Receive buffer
        
        while (true) { // Run forever, accepting and servicing connections
            Socket clntSock = servSock.accept();
            SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
            System.out.println("Handling client at " +clientAddress);
            
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            
            while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                out.write(receiveBuf, 0, recvMsgSize);
            }
            
            clntSock.close(); // Close the socket. We are done with this client!
            /* NOT REACHED */
        }
    }
}
