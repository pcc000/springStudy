package spring.study.demo.common;

import spring.study.demo.support.exception.UnifiedException;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/23 19:31
 * @Description:
 */
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 2994494589314183496L;
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3-9][0-9]{9}$");
    private boolean success;
    private T data;
    private String errCode;
    private String errMsg;

    public ResponseResult() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static <T> ResponseResult ok(T data) {
        ResponseResult<T> result = new ResponseResult();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static ResponseResult ok() {
        return ok((Object)null);
    }


    public static ResponseResult fail(String errCode, String errMsg) {
        ResponseResult result = new ResponseResult();
        result.setSuccess(false);
        result.setErrCode(errCode);
        result.setErrMsg(errMsg);
        return result;
    }

    public static ResponseResult fail(UnifiedException e) {
        return fail(e.getCode(), e.getMessage());
    }

}
