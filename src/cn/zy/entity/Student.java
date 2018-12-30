package cn.zy.entity;

import cn.zy.interfaces.Person;

import java.io.Serializable;

public class Student implements Person,Serializable {
    private  int id ;
    public  String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student() {
    }

    private void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void say() {
        System.out.println("person实现类student-------");
    }
}
