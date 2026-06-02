package com.ytseries.sms.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;


@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    int reqId = ThreadLocalRandom.current().nextInt();
// pointCut is for execution for all the method in service package and it classes
    @Pointcut("execution(* com.ytseries.sms.services..*(..))")
    public void serviceLogging(){}

    @Around(" serviceLogging()")
    public Object loggingMethodExecution(ProceedingJoinPoint pjp) throws  Throwable{
//        get service class name
        String serviceName = pjp.getTarget().getClass().getSimpleName();
//        get Method Name
        String methodName = pjp.getSignature().getName();

        LocalDateTime startTime = LocalDateTime.now();
        String startTimerStr = startTime.format(FORMATTER);

//        Entry Log
        log.info("========================");
        log.info("Service: {},Method:{}",serviceName,methodName);
        log.info("=========================");
        log.info("startTimer:{}",startTimerStr);

        Object result = null;
        Throwable exception = null;

        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable1) {
            exception = throwable1;
            throw exception;
        }finally {
//            Method End Log
         LocalDateTime endTime = LocalDateTime.now();
         String endTimeStr = endTime.format(FORMATTER);

            Duration duration = Duration.between(startTime,endTime);
            long durationsMillis = duration.toMillis();

            log.info("===========Start reqId{}===========",reqId);
            if (exception !=null){
                log.error("Status: Failed | exception: {}",exception.getMessage());
            }
            log.info("Service: {},Method:{}",serviceName,methodName);
            log.info("===========End reqId{}============",reqId);
            log.info("EndTimer:{}",endTimeStr);
            log.info("executionTime: {}",durationsMillis);
            log.info("========================");

        }
    }
}
