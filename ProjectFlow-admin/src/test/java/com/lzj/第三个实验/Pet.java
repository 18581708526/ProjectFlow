package com.lzj.第三个实验;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private String name;
    private int age;
    private String type;

    @Override
    public String toString() {
        return "宠物{" +
                "名称='" + name + '\'' +
                ", 年龄=" + age +
                ", 类型='" + type + '\'' +
                '}';
    }
}
