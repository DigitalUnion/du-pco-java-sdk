package com.my.test;

import cn.shuzilm.dupco.Receiver;
import cn.shuzilm.dupco.utils.EncoderUtils;

import java.util.Base64;

public class ReceiverMain {
    public static void main(String[] args)throws Exception {
//        String src = "tcJ8eZgXGF+/LJ6YyeIZirYLhZN1PMfGOjVkcsTrtwtraL73BmQMYKgODgTFFF2v5Lp6J5MXLMZO4h5kzm3qr5I4B/GxIlcSX6I1Sd0NwGwKTFoBxlL2ldvLl68v9AEi92m3yQt6cI538xZNZiFdnGD7kfWDWdIo0pgESoXBDhpEctisszZWEm9ywt/HpsK6";
//        String src = "rQmBRlmFT62DCVWI9iLDB7LDG++Y6TLRftMUiHjj/mmgnoD8iObJ57EDyOVedLWESCNfEebGXs8IT6OHV69R0eA2OwPdP2m/zJETyx6RZfEFY2lMHuqQnIe6a518Et+R2rpT3a8oo7c1f21QpZ+LhdtAfzy/rVVa9iIRXT/G4S+zDOdJ99r7NuuepiXyXBsV";
        String key = "E17_vENFRNvMcqB2";
//        byte[] data = Receiver.DecodeData(src, key.getBytes());
//        System.out.println(new String(data));
        String src = "[{\"device_id\":\"dev_id\",\"application_name\":\"com.test.aaa\",\"app_package_name\":\"\",\"app_version\":\"4.5.0.0\",\"scene_type\":\"MAT15\",\"os\":\"android\",\"ts\":\"2023-12-29 10:01:52\"}]";
        String encodeData = getEncodeData(src.getBytes(), key.getBytes());
        System.out.println(encodeData);
    }
    public static String getEncodeData(byte[] data,byte[] secret) throws Exception {
        byte[] encode = EncoderUtils.encode(data, secret);
        String encodeToString = Base64.getEncoder().encodeToString(encode);
        return encodeToString;
    }
}
