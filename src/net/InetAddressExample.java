package net;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InetAddressExample {
    
    /**
     * InetAddress 类代表了一个网络目标地址，包括主机名和数字类型的地址信息
     * 该类有两个子类，Inet4Address 和 Inet6Address，分别对应了目前 IP 地址的两个版本。InetAddress
     * 实例是不可变的，一旦创建，每个实例就始终指向同一个地址。
     * 
     * NetworkInterface 类提供了访问主机所有接口的信息的功能。 这个功能非常有用， 比如当一个程序需要通知其他程序其 IP 地址时就会用到。
     * 
     * 该方法 打印出与本地主机关联的所有 IP 地址，包括 IPv4 和 IPv6，然后对于每个在命令行中指定的主机，打印出其相关的主机名和地址。
     */
    public static void main(String[] args) {
        try {
             // 获取主机的网络接口列表
             // 静态方法 getNetworkInterfaces()返回一个列表，其中包含了该主机每一个接口所对应的NetworkInterface 类实例。
            Enumeration<NetworkInterface> interfaseList = NetworkInterface.getNetworkInterfaces();
             // 空列表检测：通常情况下，即使主机没有任何其他网络连接，回环接口也总是存在的。因此，只要当一个主机根本没有网络子系统时，列表检测才为空。
            if (interfaseList == null) {
                System.out.println("--No interfaces found--");
            } else {
                while (interfaseList.hasMoreElements()) {
                    NetworkInterface iface = (NetworkInterface) interfaseList.nextElement();
                    System.out.println("Interface " + iface.getName() + " : "); // 列表中每个接口的地址
                    // 获取与接口相关联的地址
                    // getInetAddresses()方法返回了另一个 Enumeration 类对象，其中包含了 InetAddress 类的
                    // 实例，即该接口所关联的每一个地址。根据主机的不同配置，这个地址列表可能只包含 IPv4
                    // 或 IPv6 地址，或者是包含了两种类型地址的混合列表。
                    Enumeration<InetAddress> addrList = iface.getInetAddresses();
                    if ( !addrList.hasMoreElements() ) {
                        System.out.println("\t(No addresses for thisinterface)");
                    }
                    while (addrList.hasMoreElements()) {
                        InetAddress address = (InetAddress) addrList.nextElement();
                        System.out.print("\tAddress " + ((address instanceof Inet4Address ? "(v4)"
                            : (address instanceof Inet6Address ? "(v6)" :"(?)"))));
                        System.out.println(": " +address.getHostAddress()); // 获取主机名和ip地址
                    }
                    
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
