package com.lzj.lambda;

public class Test {
    public static void main(String[] args) {
        Runnable helloWorld = () -> System.out.println("hello world");
        helloWorld.run();
    }
}
