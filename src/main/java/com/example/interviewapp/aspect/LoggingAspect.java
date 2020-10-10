package com.example.interviewapp.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.interviewapp.controller.*.*Controller.*(..))")
    public void logBeforeControllerMethods(JoinPoint joinPoint) {
        logger.info("Start " + joinPoint.toLongString());
    }

    @AfterReturning("execution(* com.example.interviewapp.controller.*.*Controller.*(..))")
    public void logAfterReturningControllerMethods(JoinPoint joinPoint) {
        logger.info("Finished " + joinPoint.toLongString());
    }

    @Before("execution(* com.example.interviewapp.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        logger.info("Start " + joinPoint.toLongString());
    }

    @AfterReturning("execution(* com.example.interviewapp.service.*.*(..))")
    public void logAfterReturningServiceMethods(JoinPoint joinPoint) {
        logger.info("Finished " + joinPoint.toLongString());
    }
}
