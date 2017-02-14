package Collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionTest {
    
    public static void main(String[] args) {
        List<String> l = new ArrayList<String>();
        ArrayList<String> list = new ArrayList<String>();
        
        list.ensureCapacity(0);
        int oldCapacity = 10;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        System.out.println(newCapacity);
    }
    
}
