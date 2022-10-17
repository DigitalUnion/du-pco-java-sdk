
## Installation
pom.xml
```xml
<dependency>
    <groupId>io.github.DigitalUnion</groupId>
    <artifactId>du-pco-java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>

```

## Quickstart

```java
import cn.shuzilm.dupco.Dupco;
import cn.shuzilm.dupco.Resp;

public class PcoMain {
    public static void main(String[] args) {
        Dupco dupco = Dupco.newDataClient("cloud-test", "aa", "yDpDEihpUsF_RyWsCES1H");
//        dupco.enableTestMode();
        for (int i = 0; i < 10; i++) {
            Resp resp = dupco.doCall("idmap-query-all", "{\"f\":\"mac,imei\",\"k\":\"868862032205613\",\"m\":\"0\"}".getBytes());
            System.out.println(resp);
        }
    }
}
```
