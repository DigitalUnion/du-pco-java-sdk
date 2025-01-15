package com.my.test;

import cn.shuzilm.dupco.utils.EncoderUtils;


public class EncoderMain {

    public static void main(String[] args) throws Exception {
        String src = "hello world";
        String key = "keyaaaaakeyaaaaaaaaaaa";
        byte[] res = EncoderUtils.encode(src.getBytes(), key.getBytes());
        byte[] decode = EncoderUtils.decode(res, key.getBytes());
        System.out.println(new String(decode));
    }
}
