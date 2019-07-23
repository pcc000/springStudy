package spring.study.demo.support.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.study.demo.common.ResponseResult;

import java.util.Objects;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/20 10:21
 * @Description:
 */
@ControllerAdvice
public class UnifiedExceptionResolver1{

    private static final Logger LOG = LoggerFactory.getLogger(UnifiedExceptionResolver.class);


    @ExceptionHandler(UnifiedException.class)
    @ResponseBody
    public ResponseResult handler(UnifiedException ex) {
        LOG.error("handle exception:", ex);
        ResponseResult responseVo = new ResponseResult();
        ErrorCodeEnum errorCodeEnum = ((UnifiedException) ex).getErrorCodeEnum();
        if (Objects.nonNull(errorCodeEnum)) {
            responseVo.setErrCode(errorCodeEnum.getCode());
            String message = "";
            if (!StringUtils.isEmpty(ex.getMessage())) {
                message = "[" + ex.getMessage() + "]";
            }
            responseVo.setErrMsg(errorCodeEnum.getMsg() + message);
        } else {
            responseVo.setErrMsg(ex.getMessage());
        }

        return responseVo;
    }
}
