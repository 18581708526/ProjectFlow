package com.lzj.entity;

import lombok.Getter;

import java.util.Arrays;

public class Student {
    private int id;

    private int classes;
    private int score;
   public Student(int id, int classes, int score) {
       this.id = id;
       this.classes = classes;
       this.score = score;
   }

    public int getClasses() {
        return classes;
    }

    @Override
    public String toString() {
        return "学生：{" +
                "学号=" + id +
                ", 年纪=" + classes +
                ", 成绩=" + score +
                '}';
    }

    public static void main(String[] args) {
       Student[] students = new Student[20];
       for(int i=0;i<20;i++){
           int xh=(int)Math.random()*20+1;
           int nj=(int)Math.random()*6+1;
           int cj=(int)Math.random()*100+1;
           Student student = new Student(xh, nj, cj);
           students[i] = student;
       }
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].toString());
        }

        int[] arr={1,24,64,12,2,3,4,66};

        Arrays.stream(arr).forEach(System.out::println);


    }

    public static Student[] sort(Student[] arr){
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                if (arr[j].getClasses()> arr[j + 1].score) {
                    // 交换 arr[j+1] 和 arr[j]
                    Student temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

}
