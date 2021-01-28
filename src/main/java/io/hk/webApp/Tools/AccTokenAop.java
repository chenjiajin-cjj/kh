package io.hk.webApp.Tools;

import io.framecore.Frame.Result;
import io.framecore.Tool.PropertiesHelp;
import io.hk.webApp.Domain.Admin;
import io.hk.webApp.Domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 验证登录的aop
 */
@Order(1)
@Aspect
@Component
public class AccTokenAop {


    @Pointcut("execution(* io.hk.webApp.Controller..*.*(..))")
    private void tokenControllerAspect() {
    }

    private final Logger LOG = LoggerFactory.getLogger(AccTokenAop.class);

    @Autowired
    private SystemUtil systemUtil;

    @Around("tokenControllerAspect()")
    public Object beforeControll(ProceedingJoinPoint pjp) throws Throwable {
        Method method = getMethod(pjp);
        boolean auth = true;
        AccessToken accessToken = method.getAnnotation(AccessToken.class);
        if (accessToken != null) {
            auth = accessToken.Token();
        }
        LOG.info("开始进行登录验证...");
        if (auth) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            User user = systemUtil.getUser(request);
            Admin admin = systemUtil.getAdmin(request);
            if (null == user && null == admin) {
                LOG.info("验证失败...");
                return Result.failure(-2, "请重新登陆");
            }
            if(null == user){
                user = new User();
                user.setLastloginTime(admin.getLastloginTime());
            }
            long nowTimes = System.currentTimeMillis();
            long lastLoginTime = nowTimes;
            if (null != user.getLastloginTime()) {
                lastLoginTime = user.getLastloginTime();
            }
            int day = Integer.parseInt(PropertiesHelp.getApplicationConf("loginTime"));
            if ((day * 86400000 + lastLoginTime) < nowTimes) {
                LOG.info("验证失败...");
                return Result.failure(-2, "请重新登陆");
            } else {
                LOG.info("验证通过...");
                return objectProceedingJoinPoint(pjp);
            }
        }
        LOG.info("接口放行，取消验证...");
        return objectProceedingJoinPoint(pjp);
    }

    private Object objectProceedingJoinPoint(ProceedingJoinPoint pjp) throws Throwable {
        Object returnValue;
        Object[] args = pjp.getArgs();
        returnValue = pjp.proceed(args);
        return returnValue;
    }

    private Method getMethod(ProceedingJoinPoint pjp) {
        Object target = pjp.getTarget();
        String methodName = pjp.getSignature().getName();
        Signature sig = pjp.getSignature();
        MethodSignature msig;
        Method method = null;
        try {
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) sig;
            Class[] parameterTypes = msig.getMethod().getParameterTypes();
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return method;
    }
}
