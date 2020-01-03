package com.runyuanj.upload.test.entity;

/**
 * @author: runyu
 * @date: 2019/12/30 15:33
 */
public class B extends A {

    public static String b;

    static {
        b = "default B";
    }


    B(String param) {
        //super(param + "->a");
        b = param;
        System.out.println("Class B.b = " + b);
    }

    {
        System.out.println("This is Class B");
    }

    static {
        System.out.println("B Static method");
    }

    public static void main(String[] args) {
        //B b = new B("b");
        //System.out.println(B.b);
        //
        System.out.println(B.b);
        System.out.println(B.a);
        B b = new B("b");
        System.out.println(B.b);
        System.out.println(B.a);
        //System.out.println(B.a);
    }

}
