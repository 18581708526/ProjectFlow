package com.lzj.test;

import com.lzj.ProjectFlowApplication;
import com.lzj.entity.Person;
import com.lzj.filedetail.domain.FileDetail;
import com.lzj.filedetail.mapper.FileDetailMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(classes = ProjectFlowApplication.class)
public class BaseTest {
    @Autowired
    private FileDetailMapper fileDetailMapper;




    @Test
    public void test(){
        List<FileDetail> fileDetails = fileDetailMapper.selectList(null);
        Map<Long, FileDetail> map = fileDetails.stream().collect(Collectors.toMap(FileDetail::getId, Function.identity()));
        map.forEach((k,v)->{
            System.out.println(k+" "+v);
        });
        fileDetails.forEach(System.out::println);
        System.out.println("test");
    }

    //Stream流常用方法练习

    /**
     * stream不存储数据，而是按照特定的规则对数据进行计算，一般会输出结果。
     * stream不会改变数据源，通常情况下会产生一个新的集合或一个值。
     * stream具有延迟执行特性，只有调用终端操作时，中间操作才会执行。
     */
    @Test
    public void Streamtest(){
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd", "ee", "ff");
        //创建一个顺序流
        Stream<String> stream = list.stream();
        //创建一个并行流
        Stream<String> parallelStream = list.parallelStream();
    }

    @Test
    public void StreamtestforPerson(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<Person> personlist = new ArrayList<Person>();
        personlist.add(new Person("张三", "123456789012345678", '1', new Date(1990, 1, 1), new Date(), "13800000001"));
        personlist.add(new Person("李四", "234567890123456789", '2', new Date(1992, 5, 15), new Date(), "13911111111"));
        personlist.add(new Person("王五", "345678901234567890", '1', new Date(1985, 10, 20), new Date(), "13722222222"));
        personlist.add(new Person("赵六", "456789012345678901", '2', new Date(1998, 3, 5), new Date(), "13633333333"));
        personlist.add(new Person("孙七", "567890123456789012", '1', new Date(2000, 7, 12), new Date(), "13544444444"));
        personlist.add(new Person("周八", "678901234567890123", '2', new Date(1988, 8, 25), new Date(), "13455555555"));
        personlist.add(new Person("吴九", "789012345678901234", '1', new Date(1995, 11, 30), new Date(), "13366666666"));
        personlist.add(new Person("郑十", "890123456789012345", '2', new Date(1991, 6, 8), new Date(), "13277777777"));
        personlist.add(new Person("钱十一", "901234567890123456", '1', new Date(2002, 2, 14), new Date(), "13188888888"));
        personlist.add(new Person("孙十二", "012345678901234567", '2', new Date(1987, 4, 22), new Date(), "13099999999"));

        List<Person> collect = personlist.stream().filter(person -> person.getSex() == '1').collect(Collectors.toList());
        collect.forEach(System.out::println);
        /**
         * Ranmda表达式：
         * 个人理解：
         * 1.函数式编程和正常的函数式编程的区别在于，函数式编程的函数体中，
         * 可以包含一些逻辑，而正常函数式编程中，函数体中只能包含一个表达式。
         * 2.写法上:构建的对象简化为“()”,而实际函数体方法简化为“{}”
         */
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        new Thread(()-> {

            }
        );


    }
}
