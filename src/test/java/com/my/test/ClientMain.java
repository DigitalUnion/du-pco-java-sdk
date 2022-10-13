package com.my.test;

import cn.shuzilm.dupco.Client;
import cn.shuzilm.dupco.Resp;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client("cloud-test", "aa", "yDpDEihpUsF_RyWsCES1H");
        client.enableTestMode();
        for (int i = 0; i < 1; i++) {
            Resp resp = client.doCall("idmap-query-all", "{\"f\":\"mac,imei\",\"k\":\"868862032205613\",\"m\":\"0\"}".getBytes());
            System.out.println(resp);
        }
    }
}
