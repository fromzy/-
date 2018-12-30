package cn.zy.test;

import cn.zy.entity.Student;
import cn.zy.handler.StudentHandler;
import cn.zy.interfaces.Person;
import org.junit.Test;
import java.io.*;
import java.lang.reflect.*;

public class MyTest {
    @Test//反射
    public void  t1()throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException,
            InvocationTargetException, NoSuchFieldException{
        String className = "cn.zy.entity.Student";
        //1.通过类的全路径获取Class对象
        Class c = Class.forName(className);

        //2.通过反射创建对象
        Student s = (Student) c.newInstance();//
        Student s2 = (Student) c.newInstance();

        //或者通过获取构造器来创建对象
        //获取有参构造器
        Constructor constructor =  c.getConstructor(int.class,String.class);
        //构造器赋值
        Student s3 = (Student)constructor.newInstance(12345,"zhangsan");


        //3.获取成员变量/函数
        //获取私有属性id
        Field field1 = c.getDeclaredField("id");
        //给获取到的私有成员变量赋值
        field1.setAccessible(true);//凡是私有属性，都需要强制授权
        field1.set(s,1234541451);

        //获取公有属性name
        Field field2 = c.getField("name");
        field2.set(s,"lisi");

        System.out.println(s);


        //获取成员函数
        //非私有
        Method method1 =  c.getMethod("setName",String.class);//形参类型
        //调用方法
        method1.invoke(s2,"wangwu");//让s2调用此方法


        //私有方法
        //得到私有方法setId
        Method method2 = c.getDeclaredMethod("setId",int.class);
        method2.setAccessible(true);//凡是私有属性，都需要强制授权
        method2.invoke(s2,45435525);
        System.out.println(s2);



        //还可以得到这个类的类加载器
        Object  o = c.getClassLoader();


        //得到所有这个类实现的接口
        Class[] interfaces = c.getInterfaces();

        //得到配置文件
        //关于路径；开头不加"/"，表示当前类所在的package;加"/"则表示再classpath(src）下
        // Object o2 = c.getResource("/log4j.properties");

    }

    @Test//动态代理
    public  void  t2(){

        Student ss = new Student(123313,"zhangsan");
        StudentHandler ssh = new StudentHandler(ss);

        Person lisi2 =(Person) Proxy.newProxyInstance
                (ClassLoader.getSystemClassLoader(),new Class[]{Person.class},ssh);//传入接口的类型，和InvocationHandler的实现类
        lisi2.say();
    }


    @Test//lamda
    public void  t3(){
        Person person = ()->{
            System.out.print("i am person");
        };
        person.say();
    }

    @Test//序列化
    public void t4(){
        Student s = new Student(123131,"zhangsan");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(//必须传入字节流
                    new BufferedOutputStream(
                            new FileOutputStream("D:\\mytest\\c.txt")
                    ));

            oos.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //4.释放资源
            if(oos!= null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test//反列化
    public void t5(){
        Student s = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("D:\\mytest\\c.txt")
                    )
            );
            Object o = ois.readObject();
            if(o instanceof Person){
                s = (Student)o;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //4.释放资源
            if(ois!= null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
