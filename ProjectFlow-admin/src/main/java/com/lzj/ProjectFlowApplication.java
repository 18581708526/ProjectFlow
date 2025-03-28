package com.lzj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Stack;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.lzj.**.mapper")
public class ProjectFlowApplication {
    public static void main(String[] args)
    {

        SpringApplication.run(ProjectFlowApplication.class, args);
        System.out.println("ProjectFlow启动成功");
    }
}
