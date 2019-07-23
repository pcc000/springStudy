package spring.study.demo.support.validator.annotation;

import java.lang.annotation.*;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/19 19:29
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanValid {
    String value() default "";
}
