package com.microservice.user.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonLogEnhance {

    @Pointcut("execution(public * com.microservice.user.controller..*.*(..))")
    public void webLog() {
    }
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("--------before");
        System.out.println(joinPoint.getKind());
    }

//    @AfterReturning("webLog()")
//    public void doAfter() {
//        System.out.println("-------afterReturning");
//    }

}
