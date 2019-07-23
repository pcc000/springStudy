package spring.study.demo.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/23 19:58
 * @Description:
 */
@Data
public class User {

    @NotNull(message = "userName不能为空")
    private String userName;

    @NotNull(message = "password不能为空")
    private String password;
}
