package com.lzj.test;


import com.lzj.entity.Person;
import com.lzj.entity.Product;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class test09181142 extends BaseTest{
    private static final String NAME_SIGON="liao-zijia";
    @Autowired
    private RocketMQTemplate rocketTemplate ;

    /**
     * 计算贷款金额
     */
       /* public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            if(scanner.hasNextBigDecimal()){
                BigDecimal bigDecimal = scanner.nextBigDecimal();


                System.out.println("贷款金额为"+bigDecimal);


            }else{
                System.out.println("请输入数字类型的数据");
            }
        }

    }*/

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner1 = new Scanner(System.in);
        String s = scanner1.nextLine();
        String[] split = s.split("-");
        int month=Integer.parseInt(split[1]);
        int day=Integer.parseInt(split[2]);
        switch (s){
            case "2024":
                System.out.println("第"+30*month+day+"天");
        }
/*        Product builder = new Product.Builder()
                .setProduct_id(1)
                .setProduct_name("测试")
                .setCreate_time(new Date())
                .setIsused(true)
                .build();
        System.out.println(builder);*/
        String nameSigon = test09181142.NAME_SIGON;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入第一个数字：");
        int a = scanner.nextInt();
        System.out.println("请输入第二个数字：");
        int b = scanner.nextInt();
        System.out.println("请输入第三个数字：");
        int c = scanner.nextInt();
        if(a>b){
            if(b>c){
                System.out.println("从大到小排序为："+a+b+c);
            }else{
                System.out.println("从大到小排序为："+a+c+b);
            }
        }else{//(a<b)
            if(b<c){
                System.out.println("从大到小排序为："+c+b+a);
            }else{
                System.out.println("从大到小排序为："+b+c+a);
            }
        }

    }

}
