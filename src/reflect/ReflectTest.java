package reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;
/**
 * 反射练习类：反射机制是很多Java框架的基石，而一般应用层面很少用。反射增加了应用程序的灵活性，但是牺牲了程序的性能和
 * 寻找bug的难度。如果在使用反射后，很多bug都只能在程序的运行期才能发现。因此建议程序员在平常写业务时少用
 */
public class ReflectTest {

    /*
     * 通过一个对象获得完整的包名和类名,实例化Class类对象
     */
    public void testReflec() {
        /*
         * 所有类的对象都是class实例
         */
        Demo demo = new Demo();
        System.out.println(demo.getClass().getName());
        
        Class<?> demo1 = null;
        Class<?> demo2 = null;
        Class<?> demo3 = null;
        
        try {
            demo1 = Class.forName(demo.getClass().getName()); // 这种方式最常用
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        demo2 = new Demo().getClass();
        demo3 = Demo.class;
        System.out.println("demo1:" + demo1.getName());
        System.out.println("demo2:" + demo2.getName());
        System.out.println("demo3:" + demo3.getName());
    }
    /*
     * 实例化Class类对象
     */
    public void testArr() {
        int[] temp = {1,2,3,4,5,6};
        Class<?> demo = temp.getClass().getComponentType();
        System.out.println("数组类型：" + demo.getName());
        System.out.println("数组长度  "+Array.getLength(temp)); 
        System.out.println("数组的第一个元素: "+Array.get(temp, 0)); 
        Array.set(temp, 0, 100); 
        System.out.println("修改之后数组第一个元素为： "+Array.get(temp, 0)); 
    }
    /*
     * 通过无参构造实例化对象,如果实例化对象没有无参构造函数时，会抛出异常
     */
    public void testCons(){
        Class<?> demo = null;
        try {
            demo = Class.forName("reflect.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Person per = null;
        try {
            per = (Person) demo.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        per.setName("yx");
        per.setAge(20);
        System.out.println(per.toString());
    }
    /*
     * 通过Class调用其他类中的构造函数 （也可以通过这种方式通过Class创建其他类的对象）
     */
    public void test4(){
        Class<?> demo=null; 
        try{ 
            demo=Class.forName("reflect.Person"); 
        }catch (Exception e) { 
            e.printStackTrace(); 
        } 
        Person per1=null; 
        Person per2=null; 
        Person per3=null; 
        Person per4=null; 
        //取得全部的构造函数 
        Constructor<?> cons[]=demo.getConstructors(); 
        for (int i = cons.length - 1; i >= 0; i--) {
            System.out.println("构造函数：" + cons[i]);
        }
        try{ // 顺序与实体类中构造器顺序相反
            per1=(Person)cons[3].newInstance(); 
            per2=(Person)cons[2].newInstance("Rollen"); 
            per3=(Person)cons[1].newInstance(20); 
            per4=(Person)cons[0].newInstance("Rollen",20);
            test("fdsf", 20, 456);
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
        System.out.println(per1); 
        System.out.println(per2); 
        System.out.println(per3); 
        System.out.println(per4); 
        
    }
    /*
     * 这里的Object ... args 类似于 Object[] args ,和js参数数组arguments类似
     */
    public void test(String name, Object ... args){
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        System.out.println(args.toString());
    }
    /*
     * 返回一个类实现的接口
     */
    public void testGetInterface(){
        Class<?> demo=null; 
        try{ 
            demo=Class.forName("reflect.Person"); 
        }catch (Exception e) { 
            e.printStackTrace(); 
        } 
        //保存所有的接口 
        Class<?> intes[] = demo.getInterfaces();
        for (int i = 0; i < intes.length; i++) { 
            System.out.println("实现的接口   "+intes[i].getName()); 
        } 
    }
    /*
     * 取得其他类的全部属性吧，最后我讲这些整理在一起，也就是通过class取得一个类的全部框架
     */
    public void getAll(){
        Class<?> demo = null; 
        try { 
            demo = Class.forName("reflect.Person"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        System.out.println("===============本类属性========================"); 
        // 取得本类的全部属性 
        Field[] field = demo.getDeclaredFields(); 
        for (int i = 0; i < field.length; i++) { 
            // 权限修饰符 
            int mo = field[i].getModifiers(); 
            String priv = Modifier.toString(mo); 
            // 属性类型 
            Class<?> type = field[i].getType(); 
            System.out.println(priv + " " + type.getName() + " " 
                    + field[i].getName() + ";"); 
        } 
        System.out.println("===============实现的接口或者父类的属性========================"); 
        // 取得实现的接口或者父类的属性 
        Field[] filed1 = demo.getFields(); 
        for (int j = 0; j < filed1.length; j++) { 
            // 权限修饰符 
            int mo = filed1[j].getModifiers(); 
            String priv = Modifier.toString(mo); 
            // 属性类型 
            Class<?> type = filed1[j].getType(); 
            System.out.println(priv + " " + type.getName() + " " 
                    + filed1[j].getName() + ";"); 
        } 
    }
    /*
     * 使用反射调用其它类的方法
     */
    public void invokeMethod(){
        Class<?> demo = null; 
        try { 
            demo = Class.forName("reflect.Person"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        try{ 
            //调用Person类中的sayChina方法 
            Method method=demo.getMethod("sayChina"); 
            method.invoke(demo.newInstance()); 
            //调用Person的sayHello方法 
            method=demo.getMethod("sayHello", String.class, int.class); // 方法名 ， 参数类型， 参数类型
            method.invoke(demo.newInstance(),"Rollen", 20); // 对象， 参数， 参数
        }catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }
    /*
     * 调用其它类的get,set方法
     */
    @Test
    public void testSetGet(){
        Class<?> demo = null; 
        Object obj=null; 
        try { 
            demo = Class.forName("reflect.Person"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        try{ 
         obj=demo.newInstance(); 
        }catch (Exception e) { 
            e.printStackTrace(); 
        } 
        setter(obj,"Name","刘翔",String.class); 
        getter(obj,"Name"); 
    }
    
    /** 
     * @param obj 
     *            操作的对象 
     * @param att 
     *            操作的属性 
     * */ 
    public static void getter(Object obj, String att) { 
        try { 
            Method method = obj.getClass().getMethod("get" + att); 
            System.out.println(method.invoke(obj)); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
  
    /** 
     * @param obj 
     *            操作的对象 
     * @param att 
     *            操作的属性 
     * @param value 
     *            设置的值 
     * @param type 
     *            参数的属性 
     * */ 
    public static void setter(Object obj, String att, Object value, 
            Class<?> type) { 
        try { 
            Method method = obj.getClass().getMethod("set" + att, type); 
            method.invoke(obj, value); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}
