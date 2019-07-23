package spring.study.demo.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.study.demo.common.JsonUtil;
import spring.study.demo.common.ResponseResult;
import spring.study.demo.domain.User;
import spring.study.demo.support.validator.annotation.BeanValid;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/23 19:56
 * @Description:
 */
@Component
@RequestMapping("/testController")
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    @BeanValid
    public ResponseResult test(@RequestBody User user){
        System.out.println("user:"+JsonUtil.toJson(user));
        return ResponseResult.ok();
    }

}
