package com.lzj.entity;


import com.lzj.common.Builder;

public class Adress {
    public static String SSX="ASX";
    public final String addressname;
    private String addresscode;

    public Adress(String addressname, String addresscode) {
        this.addressname = addressname;
        this.addresscode = addresscode;
    }
    public Adress(){
        this.addressname = "四川省成都市";
    }
    public String getAddressname() {
        return addressname;
    }

    public String getAddresscode() {
        return addresscode;
    }

    static {
        System.out.println("默认地址为四川省成都市");
    }
    public static void test(){
        System.out.println("静态方法");
    }

}

