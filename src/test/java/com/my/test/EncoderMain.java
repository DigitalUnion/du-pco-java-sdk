package com.my.test;

import cn.shuzilm.dupco.utils.EncoderUtils;

import java.util.Base64;

public class EncoderMain {
    public static void main(String[] args) throws Exception {
        String src = "hello world";
        String key = "keyaaaaakeyaaaaaaaaaaa";
        byte[] res = EncoderUtils.encode(src.getBytes(), key.getBytes());
        byte[] decode = EncoderUtils.decode(res, key.getBytes());
        System.out.println(new String(decode));
        System.out.println(Base64.getEncoder().encodeToString(res));

//        byte[] bytes = Base64.getDecoder().decode("rubMo/ZeOCFhcWRsXIhWG7/MY8P3HPhgb4VEE5crXGA=");
//        byte[] decode1 = EncoderUtils.decode(bytes, key.getBytes());
//        System.out.println(new String(decode1));
    }
}
