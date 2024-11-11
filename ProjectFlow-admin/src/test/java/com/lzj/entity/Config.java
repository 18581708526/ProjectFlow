package com.lzj.entity;

import java.util.Date;

public class Config {

    public Adress getAdress(){
        return new Adress("中国", "110000");
    };

    public Product getProduct(){
        return new Product.Builder().setProduct_id(1).setProduct_name("华为").build();
    };
    public Person getPerson(){
        return new Person("张三", "001", '男', new Date(), new Date(), "备注");
    }
}
