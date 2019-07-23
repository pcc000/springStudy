package spring.study.demo.support.exception;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/19 19:56
 * @Description:
 */
public class UnifiedException extends RuntimeException {

    private ErrorCodeEnum errorCodeEnum;

    private String msg;

    private String code;

    public UnifiedException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    public UnifiedException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.errorCodeEnum = errorCodeEnum;
        this.msg = msg;
    }

    public UnifiedException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ErrorCodeEnum getErrorCodeEnum(){
        return errorCodeEnum;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
}
