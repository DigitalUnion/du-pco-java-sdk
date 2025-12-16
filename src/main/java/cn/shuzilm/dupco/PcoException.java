package cn.shuzilm.dupco;


public class PcoException extends RuntimeException{
    // 自定义错误码（可根据业务需求定义为String或int）
    private Integer code;
    public PcoException() {
        super();
    }

    public PcoException(String message) {
        super(message);
    }

    /**
     * 带错误码和消息的构造方法（推荐业务层使用）
     * @param code 自定义错误码
     * @param message 异常描述信息
     */
    public PcoException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 带异常消息和根源的构造方法（关键：保留堆栈信息）
     * @param message 异常描述信息
     * @param cause 原始异常（根源异常）
     */
    public PcoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带错误码、消息和根源的构造方法（最完整的构造方式）
     * @param code 自定义错误码
     * @param message 异常描述信息
     * @param cause 原始异常（根源异常）
     */
    public PcoException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    // Getter和Setter方法
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
