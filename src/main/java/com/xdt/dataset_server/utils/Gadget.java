package com.xdt.dataset_server.utils;

/**
 * @author XDT
 * @ClassName Gadget
 * @Description: TODO
 * @Date 2023/3/8 10:03
 **/
public class Gadget {
    /*判断第一个字符是不是大写, 如果不是，转换为首字符大写*/
    public static String isUppercase(String str){
        char c = str.charAt(0);
        if(!Character.isUpperCase(c)){
            char[] cs = str.toCharArray();
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return str;
    }
}
