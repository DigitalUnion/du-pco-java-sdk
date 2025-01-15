package com.my.test;

import cn.shuzilm.dupco.Dupco;
import sun.security.provider.MD5;

public class PcoMain {
    public static void main(String[] args) {
//        Dupco dupco = Dupco.newDataClient("xlDuFAjj", "zm", "IzrAZFK3QlCcVr8X");
//        for (int i = 0; i < 1; i++) {
//            byte[] resp = dupco.doCall("geo-location", "{\"device_id\": [\"ac7a9726101d9bffed4a71ece9523572\"]}".getBytes());
//            System.out.println(new String(resp));
//        }

        Dupco dupco = Dupco.newDataClient("cloud-test", "aa", "yDpDEihpUsF_RyWsCES1H");
//        dupco.enableTestMode();
        for (int i = 0; i < 1; i++) {
            byte[] resp = dupco.doCall("fence-sub", "{\"client_id\":\"cloud-test\",\"fence_id\":\"\",\"fence_name\":\"3.0 版本测试围栏\",\"fence_type\":1,\"center_point\":\"116.370133,39.896172\",\"radius\":1000,\"points\":\"\",\"id_type\":\"imei\",\"effective_days\":1,\"limit\":100}".getBytes());
            System.out.println(new String(resp));
        }
    }
}
