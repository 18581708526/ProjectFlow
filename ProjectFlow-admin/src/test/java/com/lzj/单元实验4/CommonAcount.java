package com.lzj.单元实验4;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonAcount implements IAcount {
    //持卡人姓名
    private String acount_name;
    //银行卡号
    private long acount_num;
    //余额
    private double acount_balance;
    //账户类别  1类和2类
    private String acount_type;
    public static final String ACOUNT_TYPE_Ⅰ ="1类";
    public static final String ACOUNT_TYPE_Ⅱ ="2类";

    public CommonAcount(String acount_name, long acount_num, double acount_balance, String acount_type) {
        this.acount_name = acount_name;
        this.acount_num = acount_num;
        this.acount_balance = acount_balance;
        this.acount_type = acount_type;
    }

    //存款方法
    @Override
    public void deposit(double amount) {
        //一类  存款无限金额 取款每次最多十万
        //二类  存款小于10万 取款每次最多5000
        if(ACOUNT_TYPE_Ⅰ.equals(acount_type)){
            this.acount_balance+=amount;
            System.out.println("存款成功，一类账户余额为"+acount_balance);
        }else if(ACOUNT_TYPE_Ⅱ.equals(acount_type)){
            if(amount<100000.00){
                this.acount_balance+=amount;
                System.out.println("存款成功，二类账户余额为"+acount_balance);
            }else{
                System.out.println("存款金额超过上限,二类存款不得超过100000元");
            }
        }else{
            System.out.println("请输入正确的账户类型");
        }
    }

    //取款方法
    @Override
    public void withdrawal(double amount) {
        if(ACOUNT_TYPE_Ⅰ.equals(acount_type)){
            //先判断是否合法,再判断余额大于取款金额
         if(amount<=100000.00){
                 if (this.acount_balance>=amount){
                     this.acount_balance-=amount;
                     System.out.println("取款成功，一类账户余额为"+acount_balance);
                 }else{
                     System.out.println("取款金额超过账户余额,余额为"+acount_balance);
                 }
             }else {
             System.out.println("取款金额超过上限,一类账户取款不得超过100000元");
            }
        }else if(ACOUNT_TYPE_Ⅱ.equals(acount_type)){
            //二类账户，每次取款金额不能超过5000
            if(amount<=5000.00){
                if(this.acount_balance>=amount){
                    this.acount_balance-=amount;
                    System.out.println("取款成功，二类账户余额为"+acount_balance);
                }else{
                    System.out.println("取款金额超过账户余额账户余额为"+acount_balance);
                }
            }else{
                System.out.println("取款金额超过上限,二类取款款不得超过5000元");
            }
        }else{
            System.out.println("请输入正确的账户类型");
        }
    }

}
