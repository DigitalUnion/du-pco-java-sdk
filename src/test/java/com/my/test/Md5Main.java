package com.my.test;

import cn.shuzilm.dupco.utils.Md5Utils;

public class Md5Main {
    public static void main(String[] args) {
        String body = "a"; //请求体
        String timestamp = "a"; //请求头timestamp的值
        String secretVal = "a"; //密钥value
        String xSmSign = "47bce5c74f589f4867dbd57e9ca9f808"; //请求头X-Sm-Sign的值
        String mySign = Md5Utils.string2MD5(body+timestamp+secretVal); //生成签名
        System.out.println(mySign);
        boolean pass = mySign.equals(xSmSign); //与数盟签名对比
        System.out.println(pass);
    }
}
