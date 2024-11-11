package com.lzj.test;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * LinkList实现mq消息队列
 */
public class LinktoMq {

    public static final Object obj = new Object();
    public static final LinkedList<Object> linkList = new LinkedList<>();

    public static void main(String[] args) {

        new Thread(()-> {
            Scanner scanner = new Scanner(System.in);
            while(true) {
                String line = scanner.nextLine();
                synchronized (obj) {
                    linkList.add(line);
                    obj.notify();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object removedFirst = LinktoMq.linkList.removeFirst();
                        System.out.println("客户端收到消息："+removedFirst);
                    }catch (Exception e) {
                        synchronized (LinktoMq.obj) {
                            try {
                                LinktoMq.obj.wait();
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }


}
