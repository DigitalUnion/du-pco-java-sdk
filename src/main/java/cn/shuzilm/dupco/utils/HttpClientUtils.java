package cn.shuzilm.dupco.utils;

import cn.shuzilm.dupco.PcoException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpClientUtils {
    private static final String ENCODING = "UTF-8";
    public static final int DEFAULT_CONNECT_TIMEOUT = 6000;
    public static final int DEFAULT_READ_TIMEOUT = 6000;
    public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 6000;
    private static final int MAX_TOTAL = 1024;
    private static final int MAX_PER_ROUTE = 512;

    private static RequestConfig requestConfig;
    private static PoolingHttpClientConnectionManager connectionManager;
    private static BasicCookieStore cookieStore;
    private static HttpClientBuilder httpBuilder;
    private static CloseableHttpClient httpClient;
    private static CloseableHttpClient httpsClient;
    private static SSLContext sslContext;


    static {
        try {
            sslContext = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[] {tm}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(DEFAULT_CONNECT_REQUEST_TIMEOUT))
                .build();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cookieStore = new BasicCookieStore();
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        httpBuilder = HttpClientBuilder.create();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpBuilder.setDefaultCookieStore(cookieStore);
        httpClient = httpBuilder.build();
        httpsClient = httpBuilder.build();
    }




    public static HttpClientResult doPost(String url, Map<String, String> headers, byte[] body, boolean https) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        setHeader(headers, httpPost);
        httpPost.setEntity(new ByteArrayEntity(body, ContentType.APPLICATION_OCTET_STREAM));
        httpPost.setHeader("Content-type", "application/octet-stream");
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost);
            }
        } finally {
            release(httpResponse);
        }
    }


    public static void setHeader(Map<String, String> params, HttpUriRequestBase httpMethod) {
        // 封装请求头
        if (null != params && !params.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpUriRequestBase httpMethod) {
        try {
            // 执行请求
            httpResponse = httpClient.execute(httpMethod);
            // 获取返回结果
            if (httpResponse != null) {
                byte[] content = new byte[0];
                if (httpResponse.getEntity() != null) {
                    content = EntityUtils.toByteArray(httpResponse.getEntity());
                }
                return new HttpClientResult(httpResponse.getCode(), content);
            }
        } catch (IOException e) {
            throw new PcoException(e.getMessage());
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    public static void release(CloseableHttpResponse httpResponse) {
        // 释放资源
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                throw new PcoException(e.getMessage());
            }
        }
    }

}
