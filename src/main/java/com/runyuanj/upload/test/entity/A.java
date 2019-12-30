package com.runyuanj.upload.test.entity;

import com.runyuanj.upload.test.parent.Fu;

/**
 * @author: runyu
 * @date: 2019/12/30 15:33
 */
public class A extends Fu {

    public static String a;

    A () {
        a = "default A";
    }

    A (String param) {
        a = param;
        System.out.println("Class A.a = " + a);
    }

    {
        System.out.println("This is Class A");
    }

    static {
        System.out.println("A Static method");
    }

}
