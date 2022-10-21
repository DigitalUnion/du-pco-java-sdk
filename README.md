
## Installation
pom.xml
```xml
<dependency>
    <groupId>io.github.DigitalUnion</groupId>
    <artifactId>du-pco-java-sdk</artifactId>
    <version>1.0.3</version>
</dependency>

```

## Quickstart

### 发送请求
```java
import cn.shuzilm.dupco.Dupco;
import cn.shuzilm.dupco.Resp;

public class PcoMain {
    public static void main(String[] args) {
        Dupco dupco = Dupco.newDataClient("cloud-test", "aa", "yDpDEihpUsF_RyWsCES1H");
        for (int i = 0; i < 10; i++) {
            Resp resp = dupco.doCall("idmap-query-all", "{\"f\":\"mac,imei\",\"k\":\"868862032205613\",\"m\":\"0\"}".getBytes());
            System.out.println(resp);
        }
    }
}
```

### 加解密
```java
import cn.shuzilm.dupco.utils.EncoderUtils;

public class EncoderMain {
    public static void main(String[] args) throws Exception {
        String src = "hello world";
        String key = "keyaaaaakeyaaaaa";
        byte[] res = EncoderUtils.encode(src.getBytes(), key.getBytes());
        byte[] decode = EncoderUtils.decode(res, key.getBytes());
        System.out.println(new String(decode));
    }
}
```

### 推送服务接收端解密
```java
import cn.shuzilm.dupco.Receiver;
import cn.shuzilm.dupco.utils.EncoderUtils;

import java.util.Base64;

public class ReceiverMain {
    public static void main(String[] args) throws Exception {
        //明文
        String src = "hello";
        //密钥 16位
        String secret = "aaaaaaaaaaaaaaaa";
        //加密
        String encodeData = getEncodeData(src.getBytes(), secret.getBytes());
        System.out.println(encodeData);
        //解密
        byte[] data = Receiver.DecodeData(encodeData, secret.getBytes());
        System.out.println(new String(data));
    }

    //获取密文方法

    /**
     * 
     * @param data 明文
     * @param secret    密钥
     * @return  密文 base64格式
     * @throws Exception
     */
    public static String getEncodeData(byte[] data,byte[] secret) throws Exception {
        byte[] encode = EncoderUtils.encode(data, secret);
        String encodeToString = Base64.getEncoder().encodeToString(encode);
        return encodeToString;
    }
}
```
