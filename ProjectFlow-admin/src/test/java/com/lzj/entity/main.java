package com.lzj.entity;




import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class main {
    static final Logger logger = LoggerFactory.getLogger(main.class);
    static void printClassInfo(Class cls) {
        int xh=1;
        System.out.println(xh+++".Class name: " + cls.getName());
        System.out.println(xh+++".Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println(xh+++".Package name: " + cls.getPackage().getName());
        }
        System.out.println(xh+++".is interface: " + cls.isInterface());
        System.out.println(xh+++".is enum: " + cls.isEnum());
        System.out.println(xh+++".is array: " + cls.isArray());
        System.out.println(xh+++".is primitive: " + cls.isPrimitive());
        System.out.println("--------------------分割线-------------------------");
    }

}

class test1{
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        main.printClassInfo("".getClass());
        main.printClassInfo(Runnable.class);
        main.printClassInfo(java.time.Month.class);
        main.printClassInfo(String[].class);
        main.printClassInfo(int.class);
    }

}
class test2{
    public static void main(String[] args)  {


        Class<?> aClass = null;
        try {
            aClass = Class.forName("com.lzj.entity.Adress");
            Adress adress=(Adress)aClass.newInstance();
            Field addressname = aClass.getDeclaredField("addressname");
            addressname.setAccessible(true);
            addressname.set(adress,"中国");
            main.logger.info("adressname:{}",adress.getAddressname());
            System.out.println(adress.getAddressname());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

}
class test3{
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        String s1="第一个";
        Class<? extends String> aClass = s1.getClass();
        Field value = aClass.getDeclaredField("value");
        value.setAccessible(true);
        System.out.println(s1);
        System.out.println(s1.hashCode());
        char[] o = (char[])value.get(s1);
        o[0]='第';
        o[1]='二';
        o[2]='个';
        System.out.println(s1);
        System.out.println(s1.hashCode());

    }


}
