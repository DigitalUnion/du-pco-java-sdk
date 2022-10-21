package com.my.test;

import cn.shuzilm.dupco.Receiver;
import cn.shuzilm.dupco.utils.EncoderUtils;

import java.util.Base64;

public class ReceiverMain {
    public static void main(String[] args) throws Exception {
        String src = "hello";
        String secret = "aaaaaaaaaaaaaaaa";
        String encodeData = getEncodeData(src.getBytes(), secret.getBytes());
        System.out.println(encodeData);
        byte[] data = Receiver.DecodeData(encodeData, secret.getBytes());
        System.out.println(new String(data));
    }

    public static String getEncodeData(byte[] data,byte[] secret) throws Exception {
        byte[] encode = EncoderUtils.encode(data, secret);
        String encodeToString = Base64.getEncoder().encodeToString(encode);
        return encodeToString;
    }
}
