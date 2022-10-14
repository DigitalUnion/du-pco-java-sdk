package cn.shuzilm.dupco;

public class Resp {
    // Code response code
    //
    // |code  | describe                        |
    // |------|---------------------------------|
    // |0     | success                         |
    // |10000 | IP not in whitelist             |
    // |10001 | Request path error              |
    // |10002 | Internal server error           |
    // |10100 | Param cilent_id required        |
    // |10101 | Param client_id not found       |
    // |10102 | This service is not activated   |
    // |10200 | Secret key required             |
    // |10201 | Secret not found                |
    // |10202 | Decode failed                   |
    // |10203 | Get request body failed         |
    // |10300 | Service not found               |
    // |10999 | Other error                     |
    //
    // code 0 means success, others means errors
    private int code;
    // Msg the message of response
    // if code not 0, the msg will tell you the reason
    private String msg;
    // Data the data of response, The responses are different depending on the service
    // if code not 0, the data will be nil
    private Object data;

    public Resp() {
    }

    public Resp(int code, String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public Resp(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
