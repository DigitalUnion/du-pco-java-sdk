package com.my.test;

import cn.shuzilm.dupco.utils.HttpClientResult;
import cn.shuzilm.dupco.utils.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpMain {

    public static void main(String[] args) {
        String url = "http://172.17.129.204:8427/v1/rule/dict";
        Map<String,String> header = new HashMap<String,String>();
        header.put("Content-type","application/json");
        String body = "{\"channel_tp\":1,\"server_tp\":1,\"status\":1,\"tp\":1}";
        HttpClientResult httpClientResult = HttpClientUtils.doPost(url, header, body, false);
        System.out.println(httpClientResult);
    }
}
