package com.lzj.stream.TestClass;

import java.util.Arrays;
import java.util.stream.Stream;

public class StramTest {
    public static void main(String[] args) {
        /**
         * 关于stream流的使用
         * 可以使用stream流的数据类型：数组，集合，List,Map,Set,对象？
         */
        //1.对数组使用stream流
        //1.1 过滤
        String[] starr={"213","12","123"};
        Arrays.stream(starr)//转化流对象
                .filter(s->s.startsWith("2")) //流的中间操作 ，可以有无数个中间操作
                .forEach(System.out::println); //终止操作，就是最后要输出的数据


        //1.对集合使用stream流
    }
}
