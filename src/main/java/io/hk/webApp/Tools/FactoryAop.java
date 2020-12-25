package io.hk.webApp.Tools;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 监听供应商端的aop
 */
@Aspect
@Configuration
public class FactoryAop {

    private Long startTime;
    private Long endTime;

    private final String operateLogPoint = "execution(* io.hk.webApp.Controller.Factory.*.*(..))";

    private final Logger LOG = LoggerFactory.getLogger(FactoryAop.class);

    @Autowired
    private BaseTool baseTool;

    @Pointcut(operateLogPoint)//切点
    public void webLog() {

    }

    //在方法
    @Before(value = "webLog()")
    public void beforeControll(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        baseTool.isFactory(request);
        startTime = System.currentTimeMillis();
        LOG.info("请求Url : " + request.getRequestURL().toString());
        LOG.info("请求方式 : " + request.getMethod());
        LOG.info("请求ip : " + request.getRemoteAddr());
        LOG.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOG.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));
    }
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        LOG.info("请求耗时 ： " + (endTime - startTime)+" ms");
        LOG.info("请求返回 : " + ret);
    }
}
