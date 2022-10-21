package com.my.test;

import cn.shuzilm.dupco.Dupco;
import cn.shuzilm.dupco.Resp;

public class PcoMain {
    public static void main(String[] args) {
        Dupco dupco = Dupco.newDataClient("cloud-test", "aa", "yDpDEihpUsF_RyWsCES1H");
//        client.enableTestMode();
        for (int i = 0; i < 1; i++) {
            Resp resp = dupco.doCall("idmap-query-all", "{\"f\":\"mac,imei\",\"k\":\"868862032205613\",\"m\":\"0\"}".getBytes());
            System.out.println(resp);
        }
    }
}
