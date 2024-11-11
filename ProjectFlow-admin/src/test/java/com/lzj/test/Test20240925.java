package com.lzj.test;
import com.lzj.student.domain.Student;
import com.lzj.student.service.IStudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Scanner;
public class Test20240925 extends BaseTest {

    @Autowired
    private IStudentService iStudentService;

    private static final String JISHU="奇数";
    private static final String OUSHU="偶数";
    /**
     *  数字转换星期案例
     */
    static class NumtoDay{
        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();
            String output="";
            switch (i){
                case 1:
                    output="星期一";
                    break;
                case 2:
                    output="星期二";
                    break;
                case 3:
                    output="星期三";
                    break;
                case 4:
                    output="星期四";
                    break;
                case 5:
                    output="星期五";
                    break;
                case 6:
                    output="星期六";
                    break;
                case 7:
                    output="星期天";
                    break;
                default:
                    output="请输入正确的数字！！！";
                    break;
            }
            System.out.println("结果："+output);
        }
    }

    /**
     * 输入数字，判断奇数或偶数
     */
    static class NumTOJO{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        if(i%2==0){
            System.out.println(Test20240925.OUSHU);
        }else{
            System.out.println(Test20240925.JISHU);
        }
    }
}

    /**
     * 三个变量进行排序
     */
    static class NumSort{
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入第一个数字：");
            int num1 = scanner.nextInt();
            System.out.println("请输入第二个数字：");
            int num2 = scanner.nextInt();
            System.out.println("请输入第三个数字：");
            int num3 = scanner.nextInt();
            sort(num1,num2,num3);
        }
    }


    /**
     *  封装排序方法
     *  排序方法是算法中常见的方法，这儿简单实现比较和交换进行排序
     * @param num1
     * @param num2
     * @param num3
     */
        public static void sort(int num1, int num2, int num3) {
            int target;
            if (num1 > num2) {
                target = num1;
                num1 = num2;
                num2 = target;
            }
            if (num1 > num3) {
                target = num1;
                num1 = num3;
                num3 = target;
            }
            if (num2 > num3) {
                target = num2;
                num2 = num3;
                num3 = target;
            }

            System.out.println("从小到大排序后的数字是: " + num1 + ", " + num2 + ", " + num3);
        }


@Test
public void test(){
    List<Student> i = iStudentService.selectStudentList(null);
    System.out.println(i);
}
    }
