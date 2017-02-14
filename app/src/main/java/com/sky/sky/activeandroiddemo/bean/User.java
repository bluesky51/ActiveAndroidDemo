package com.sky.sky.activeandroiddemo.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by BlueSky on 17/2/14.
 */
//创建表
@Table(name = "user",id = "_id")
public class User extends Model {
    @Column//表示是表的一个字段
    String name;
    @Column//表示是表的一个字段
    int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "age='" + age + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
