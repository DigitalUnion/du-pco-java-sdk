package cn.shuzilm.dupco.utils;

public class HttpClientResult {

    private int statusCode;
    private byte[] respBody;

    public HttpClientResult(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpClientResult(int statusCode, byte[] respBody) {
        this.statusCode = statusCode;
        this.respBody = respBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getRespBody() {
        return respBody;
    }

    public void setRespBody(byte[] respBody) {
        this.respBody = respBody;
    }

    @Override
    public String toString() {
        return "HttpClientResult{" +
                "StatusCode=" + statusCode +
                ", RespBody=" + new String(respBody) +
                '}';
    }
}
