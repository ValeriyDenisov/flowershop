package com.accenture.flowershop.be.aop;

import com.accenture.flowershop.be.api.exceptions.EntityException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logging {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.accenture.flowershop.fe.endpoints.*.*(..))")
    public void endpoints() {
    }

    @Pointcut("within(com.accenture.flowershop..*)")
    public void allMethods() {
    }

    //All methods
    @AfterThrowing(pointcut = "within(com.accenture.flowershop.be..*)", throwing = "ex")
    public void logMethodEntityException(EntityException ex) {
        logger.error(ex.getMessage());
    }

    @Before(value = "allMethods()", argNames = "joinPoint")
    public void logBeforeMethods(JoinPoint joinPoint) {
        logger.debug("Run method: " + joinPoint.getSignature().toShortString() + " Args: " + Arrays.asList(joinPoint.getArgs()));
    }

    //Endpoint
    @AfterReturning(pointcut = "endpoints()", returning = "val")
    public void logAfterEndpointMethod(JoinPoint jp, Object val) {
        logger.debug("Response from " + jp.getSignature().toShortString() + ": " + val.toString());
    }


}
