package spring.study.demo.support.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import spring.study.demo.common.JsonUtil;
import spring.study.demo.support.exception.ErrorCodeEnum;
import spring.study.demo.support.exception.UnifiedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/19 19:37
 * @Description:
 */
public class DataValidationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DataValidationUtil.class);

    private static Validator validator = null;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void validate(T bean) {
        validate(bean, Default.class);
    }

    public static <T> void validate(T bean, Class<?>... clazz) {
        StringBuffer sb = new StringBuffer();
        try {
            if (null == bean) {
                sb.append("校验的对象不能为null");
            } else {
                Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, clazz);
                if (!CollectionUtils.isEmpty(constraintViolations)) {
                    String collect = constraintViolations.stream().map(ConstraintViolation::<T>getMessage).collect(Collectors.joining(";"));
                    sb.append(collect);
                }
            }
        } catch (RuntimeException e) {
            LOG.error("数据校验发生异常", e);
            sb.append("数据校验发生异常，请检查字段注解配置是否合法");
        }
        if (!StringUtils.isEmpty(sb.toString())) {
            LOG.info("校验异常入参：{}", JsonUtil.toJson(bean));
            System.out.println(sb.toString());
            throw new UnifiedException(ErrorCodeEnum.PARAM_ILLEGAL_ERROR, sb.toString());
        }
    }
}
