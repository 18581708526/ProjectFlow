package com.lzj.单元实验4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankSystem {
    public List<CommonAcount> acountList=new ArrayList<>();
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        List<CommonAcount> acount = bankSystem.createAcount();
        for (CommonAcount acount1 : acount) {
            System.out.println(acount1);
        }
        bankSystem.deposit();
        bankSystem.withdrawal();
    }
    //创建账户方法
    public List<CommonAcount> createAcount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入开户人姓名");
        String acount_name = scanner.next();
        System.out.println("请输入开户人账号");
        long acount_num = scanner.nextLong();
        System.out.println("请输入开户人账号类别（1或2）");
        String acount_type = scanner.next();
        if ("1".equals(acount_type)) {
            CommonAcount commonAcount = new CommonAcount(acount_name, acount_num, 0, CommonAcount.ACOUNT_TYPE_Ⅰ);
            acountList.add(commonAcount);
            System.out.println("一类账户开通成功");
        }else if("2".equals(acount_type)){
            CommonAcount commonAcount = new CommonAcount(acount_name, acount_num, 0, CommonAcount.ACOUNT_TYPE_Ⅱ);
            acountList.add(commonAcount);
            System.out.println("二类账户开通成功");
        }else{
            System.out.println("请输入正确的账户类型");
        }
        return acountList;
    }
    //存款
    public void deposit(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入存款账户号");
        String name = scanner.next();
        for(CommonAcount acount:acountList){
            String s = String.valueOf(acount.getAcount_num());
            //找到要存款的账户了
            if(name.equals(s)){
                System.out.println("请输入存款金额");
                double amount = scanner.nextDouble();
                CommonAcount commonAcount = new CommonAcount(acount.getAcount_name(),acount.getAcount_num(), acount.getAcount_balance(),acount.getAcount_type());
                //调用存款方法
                commonAcount.deposit(amount);
            }else{
                System.out.println("找不到改账户");
            }
        }
    }
    //取款
    public void withdrawal(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入取款账户号");
        String name = scanner.next();
        for(CommonAcount acount:acountList){
            String s = String.valueOf(acount.getAcount_num());
            //找到要存款的账户了
            if(name.equals(s)){
                System.out.println("请输入取款金额");
                double amount = scanner.nextDouble();
                CommonAcount commonAcount = new CommonAcount(acount.getAcount_name(),acount.getAcount_num(),acount.getAcount_balance(),acount.getAcount_type());
                //调用存款方法
                commonAcount.withdrawal(amount);
            }else{
                System.out.println("找不到改账户");
            }
        }
    }
}
