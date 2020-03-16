package com.accenture.flowershop.be.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Service {

    @Pointcut(value = ("execution(public * com.accenture.flowershop.be.impl.service.*.*(..))"))
    public void services() {}

    @Around(value = "services()")
    public Object aroundService(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("Some logic...");
        Object result = jp.proceed();
        System.out.println(result);
        return result;
    }
}
