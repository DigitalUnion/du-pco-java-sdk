package cn.shuzilm.dupco;

import cn.shuzilm.dupco.utils.EncoderUtils;
import cn.shuzilm.dupco.utils.HttpClientResult;
import cn.shuzilm.dupco.utils.HttpClientUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Dupco {

    private String clientId;
    private String secretKey;
    private byte[] secretVal;
    private String domain;

    private Dupco(){

    }

    /**
     * 创建自定义客户端对象
     * @param clientId 客户id
     * @param secretKey 密钥key
     * @param secretVal 密钥val
     * @return 客户端对象
     */
    public static Dupco newClient(String domain,String clientId, String secretKey, String secretVal){
        return new Dupco(clientId,secretKey,secretVal,domain);
    }

    /**
     * 创建数据业务客户端对象
     * @param clientId 客户id
     * @param secretKey 密钥key
     * @param secretVal 密钥val
     * @return 客户端对象
     */
    public static Dupco newDataClient(String clientId, String secretKey, String secretVal){
        return new Dupco(clientId,secretKey,secretVal,Const.dataDomain);
    }

    /**
     * 创建基础业务客户端对象
     * @param clientId 客户id
     * @param secretKey 密钥key
     * @param secretVal 密钥val
     * @return 客户端对象
     */
    public static Dupco newBaseClient(String clientId, String secretKey, String secretVal){
        return new Dupco(clientId,secretKey,secretVal,Const.baseDomain);
    }

    private Dupco(String clientId, String secretKey, String secretVal, String domain) {
        this.clientId = clientId;
        this.secretKey = secretKey;
        this.secretVal = secretVal.getBytes();
        if (domain == null) {
            this.domain = Const.dataDomain;
        }else {
            this.domain = domain;
        }
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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
     * 调用远程服务
     * @param apiId 接口id
     * @param data 请求体明文
     * @return Resp 解密后的响应体明文
     */
    public byte[] doCall(String apiId,byte[] data) {
        Map<String,String> header = new HashMap<String,String>();
        header.put(Const.clientId,this.clientId);
        header.put(Const.secretKey,this.secretKey);
        header.put(Const.apiIdKey,apiId);
        header.put(Const.sdkVerKey,Const.sdkVer);
        byte[] respBody = new byte[0];
        try{
            if (data != null) {
                byte[] body = EncoderUtils.encode(data, this.secretVal);
                //sendPost
                HttpClientResult httpClientResult = HttpClientUtils.doPost(this.domain, header, body, false);
                if (httpClientResult.getStatusCode() > 400) {
                    throw  new PcoException("httpclient send post error");
                }
                respBody = httpClientResult.getRespBody();
                if (httpClientResult.getStatusCode() == 200) {
                    if (respBody != null) {
                        respBody = EncoderUtils.decode(httpClientResult.getRespBody(), this.secretVal);
                    }else {
                        throw  new PcoException("httpclient response body is null");
                    }
                }
                return respBody;
            }else {
                throw new PcoException("this data is null");
            }
        }catch (Exception e){
            return null;
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
