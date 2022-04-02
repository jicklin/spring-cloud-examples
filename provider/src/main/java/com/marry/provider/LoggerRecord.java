package com.marry.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.bouncycastle.cert.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.channels.Pipe;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mal
 * @date 2022-03-05 16:45
 */
@Aspect
@Component
public class LoggerRecord {

    private static final Logger logger = LoggerFactory.getLogger(LoggerRecord.class);


    @Around("execution(* com.marry.provider.controller..*.*(..))")
    public Object writeLog(ProceedingJoinPoint pjp) throws Throwable {
        Object target = pjp.getTarget();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        String[] parameterNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Class<?> aClass = target.getClass();

        Map<String, Object> resultMap = Maps.newHashMap();

        Method method = aClass.getMethod(methodName, parameterTypes);
        String path = getPath(aClass, method);
        resultMap.put("path", path);
        resultMap.put("aClass", aClass.getName());
        resultMap.put("method", methodName);
        Object proceed = null;
        Date start = new Date();
        try {
            proceed = pjp.proceed();
            resultMap.put("status", 1);
        } catch (Throwable e) {
            resultMap.put("status", 0);
            resultMap.put("errorMsg", e.getMessage());
            throw e;
        }finally {
            Date end = new Date();

            resultMap.put("time", end.getTime() - start.getTime());
            resultMap.put("start", DateFormatUtils.format(start, "yyyy-MM-dd HH:mm:ss.SSS"));
            resultMap.put("end", DateFormatUtils.format(end, "yyyy-MM-dd HH:mm:ss.SSS"));
            ObjectMapper objectMapper = new ObjectMapper();
            logger.info(objectMapper.writeValueAsString(resultMap));

        }
        return proceed;





    }

    private String getPath(Class<?> aClass, Method method) {
        String path = "";
        if (aClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
            String[] value = annotation.value();
            path = value[0];
            path = path.startsWith("/") ? path : "/" + path;
        }

        //RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        //if (annotation == null) {
        //    for (Annotation methodAnnotation : method.getAnnotations()) {
        //        annotation = methodAnnotation.annotationType().getAnnotation(RequestMapping.class);
        //        if (annotation != null) {
        //            break;
        //
        //        }
        //    }
        //}
        RequestMapping annotation = AnnotatedElementUtils.getMergedAnnotation(method, RequestMapping.class);
        if (annotation != null) {
            String[] value = annotation.value();
            if (value.length > 0) {
                path = value[0].startsWith("/") ? path + value[0] : "/" + path + value[0];
            }
        }


        return path;

    }


}
