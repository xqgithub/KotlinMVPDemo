package com.example.baselibrary.data.database.datasource;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * 创建数据库的第一个实体类，用来做测试用的，没有实际含义
 */
@Entity//表明该类是持久化的类【持久化含义，存入数据库文件中，作本地化处理】
public class User implements Serializable {
    private static final long serialVersionUID = -7620435178023928252L;
    @Id
    private Long primaryid;
    //    @Transient//指定GreenDao忽略此变量
    @Property(nameInDb = "id")
    private Integer id;
    @Property(nameInDb = "username")//Property 列名 nameInDb制定具体列名
    private String name;
    @Property(nameInDb = "sex")
    private String sex;

    @Generated(hash = 99915328)
    public User(Long primaryid, Integer id, String name, String sex) {
        this.primaryid = primaryid;
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(Long primaryid) {
        this.primaryid = primaryid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
