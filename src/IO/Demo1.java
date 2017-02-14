package IO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Demo1 {
    
    /**
     * JDK1.7以前处理关闭流的方式
     */
    public void oldIo() {
        OutputStream out = null;
        try {
            out = new FileOutputStream("/tmp/data.txt");
            //处理数据流
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * JDK1.7以后加入了新的功能，可以采取以下方式处理，现在不需要finally的子句，Java会对try块参数表中声明
     * 所有AutoCloseable对象自动调用close()。只有实现了java.lang.AutoCloseable接口，或者
     * java.io.Closable（实际上继随自java.lang.AutoCloseable）接口的对象，才会自动调用其close()函数。
     */
    public void newIo() {
        try (OutputStream out = new FileOutputStream("/tmp/data.txt")) {
            //处理数据流
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
