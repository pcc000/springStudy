package spring.study.demo.support.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import spring.study.demo.common.JsonUtil;
import spring.study.demo.common.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/19 19:47
 * @Description:
 */
@Component
public class UnifiedExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(UnifiedExceptionResolver.class);

    @Override
    public ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {
        ResponseResult responseVo = new ResponseResult();
        responseVo.setSuccess(false);
        if (ex instanceof UnifiedException) {
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
        } else {
            LOG.error("catch by UnifiedExceptionResolver,request:{}", JsonUtil.toJson(request.getParameterMap()), ex);
            String str = StringUtils.isEmpty(ex.getMessage()) ? "系统异常，稍后再试...".concat(ex.toString()) : ex.getMessage();
            responseVo.setErrMsg(str);
            responseVo.setErrCode("500");
        }
        String result = JsonUtil.toJson(responseVo);
        LOG.info("response:{}", result);
        write(request, response, result);
        return new ModelAndView();
    }

    protected void write(HttpServletRequest request, HttpServletResponse response, String result) {
        PrintWriter out = null;
        String charCode = "UTF-8";
        byte[] resStr = result.getBytes(StandardCharsets.UTF_8);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "*");
        try {
            out = response.getWriter();
            out.write(new String(resStr));
        } catch (UnsupportedEncodingException e) {
            LOG.error("不支持字" + charCode + "符集");
        } catch (IOException e) {
            LOG.error("获取输出流失败");
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
