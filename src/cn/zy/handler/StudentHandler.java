package cn.zy.handler;

import cn.zy.interfaces.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StudentHandler implements InvocationHandler{

    private Person studentService;//只能代理接口

    public StudentHandler(Person studentService) {
        this.studentService = studentService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(method.getName().equals("say"))
            System.out.println(" before-----");
            method.invoke(studentService,args);//如果不加流程控制（if）,目标对象的所有方法都会在这里被执行
            System.out.println(" after-----");
        return null;
    }
}
