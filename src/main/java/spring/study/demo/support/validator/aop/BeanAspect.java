package spring.study.demo.support.validator.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import spring.study.demo.support.exception.ErrorCodeEnum;
import spring.study.demo.support.exception.UnifiedException;
import spring.study.demo.support.validator.DataValidationUtil;
import spring.study.demo.support.validator.annotation.BeanValid;

import javax.validation.groups.Default;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Auther: shuyiwei
 * @Date: 2019/7/19 19:32
 * @Description:
 */
@Component
@Aspect
@Order(1)
public class BeanAspect {

    @Pointcut("@annotation(spring.study.demo.support.validator.annotation.BeanValid)")
    public void pointCut(){};

    @Before("pointCut()")
    public void checkBeanValid(JoinPoint joinPoint) throws Exception {
        try {
            Method method = getMethod(joinPoint);
            Class<?>[] group = getGroup(joinPoint, method);
            if (Objects.isNull(method)) {
                throw new UnifiedException("获取拦截方法异常");
            }
            Stream.of(joinPoint.getArgs()).forEach(obj -> {
                DataValidationUtil.validate(obj, group);
            });
        } catch (UnifiedException e) {
            throw new UnifiedException(ErrorCodeEnum.PARAM_ILLEGAL_ERROR, e.getMessage());
        }
    }

    private Class<?>[] getGroup(JoinPoint joinPoint, Method method) {
        Class<?>[] result = null;
        BeanValid beanValid = method.getAnnotation(BeanValid.class);
        if (Objects.isNull(beanValid)) {
            throw new UnifiedException("获取拦截方法注解异常");
        }
        return buildDefault();
    }

    private Class<?>[] buildDefault() {
        return new Class<?>[]{Default.class};
    }


    private Method getMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Class clazz = joinPoint.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class[] parameterTypes = methodSignature.getParameterTypes();
        Method method = null;
        try {
            method = clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new UnifiedException("method: " + methodName + ", may not be exist");
        }
        return method;
    }
}
