package cn.shuzilm.dupco;

import cn.shuzilm.dupco.utils.EncoderUtils;
import cn.shuzilm.dupco.utils.HttpClientResult;
import cn.shuzilm.dupco.utils.HttpClientUtils;
import com.google.gson.Gson;
import com.sun.source.tree.NewArrayTree;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: songhz
 * @Description: A simple way to call DigitalUnion service
 * @Date: 2022/09/07 4:09 PM
 */
public class Client {

    private String clientId;
    private String secretKey;
    private byte[] secretVal;

    /**
     * NewClient: create and return a new dupco
     * @param clientId identify of dupco
     * @param secretKey key of secret
     * @param secretVal value of secret
     */
    public Client(String clientId, String secretKey, String secretVal) {
        this.clientId = clientId;
        this.secretKey = secretKey;
        this.secretVal = secretVal.getBytes(StandardCharsets.UTF_8);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public byte[] getSecretVal() {
        return secretVal;
    }

    public void setSecretVal(byte[] secretVal) {
        this.secretVal = secretVal;
    }

    /**
     * Warnning: if you call method with test mode ,the reponse will be different. So DO NOT use it on production mode
     */
    public void enableTestMode(){
        Const.sdkVer = Const.sdkVerForTest;
    }

    /**
     * call remote function
     * @param apiId
     * @param data
     * @return Resp response from remote server
     */
    public Resp doCall(String apiId,byte[] data) {
        Map<String,String> header = new HashMap<>();
        header.put(Const.clientId,this.clientId);
        header.put(Const.secretKey,this.secretKey);
        header.put(Const.apiIdKey,apiId);
        byte[] respBody = new byte[0];
        try{
            if (data != null) {
                byte[] body = EncoderUtils.encode(data, this.secretVal);
                //sendPost
                HttpClientResult httpClientResult = HttpClientUtils.doPost(Const.domain, header, body, false);
                if (httpClientResult.getStatusCode() > 400) {
                    throw  new PcoException("httpclient send post error");
                }
                respBody = httpClientResult.getRespBody();
                System.out.println(httpClientResult.toString());
                if (httpClientResult.getStatusCode() == 200) {
                    if (respBody != null) {
                        respBody = EncoderUtils.decode(httpClientResult.getRespBody(), this.secretVal);
                    }else {
                        throw  new PcoException("httpclient response body is null");
                    }
                }
                Gson gson = new Gson();
                return gson.fromJson(new String(respBody), Resp.class);
            }else {
                throw new PcoException("this data is null");
            }
        }catch (Exception e){
            return new Resp(Const.otherErrorCode,e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", secretVal=" + new String(secretVal) +
                '}';
    }
}
