package spring.study.demo.support.exception;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/19 19:54
 * @Description:
 */
public enum ErrorCodeEnum {
    PARAM_ILLEGAL_ERROR("","");
    private String code;
    private String msg;

    private ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
