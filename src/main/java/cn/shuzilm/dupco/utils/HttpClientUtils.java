package cn.shuzilm.dupco.utils;

import cn.shuzilm.dupco.PcoException;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
                .setSocketTimeout(DEFAULT_READ_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)
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


    public static HttpClientResult doGet(String url) {
        return doGet(url, false);
    }

    public static HttpClientResult doGet(String url, boolean https){
        return doGet(url, null, null, https);
    }

    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params, boolean https){
        HttpGet httpGet = null;
        CloseableHttpResponse httpResponse = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(requestConfig);
            setHeader(headers, httpGet);
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpGet);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpGet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            release(httpResponse);
        }

        return null;
    }

    public static HttpClientResult doPost(String url, Map<String, String> headers, byte[] body, boolean https) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        setHeader(headers, httpPost);
        httpPost.setEntity(new ByteArrayEntity(body));
        httpPost.setHeader("Content-type", "application/octet-stream");
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost);
            }
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }

    public static HttpClientResult doPost(String url, Map<String, String> headers, String json, boolean https) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        setHeader(headers, httpPost);
        StringEntity stringEntity = new StringEntity(json, ENCODING);
        stringEntity.setContentEncoding(ENCODING);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost);
            }
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }



    public static HttpClientResult doDelete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpDelete);
        } finally {
            httpDelete.releaseConnection();
            release(httpResponse);
        }
    }


    public static void setHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (null != params && !params.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod) {
        try {
            // 执行请求
            httpResponse = httpClient.execute(httpMethod);
            // 获取返回结果
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                byte[] content = new byte[0];
                if (httpResponse.getEntity() != null) {
                    content = EntityUtils.toByteArray(httpResponse.getEntity());
                }
                return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
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
