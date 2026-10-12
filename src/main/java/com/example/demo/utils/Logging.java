package com.example.demo.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

	private static final Logger log=LoggerFactory.getLogger(Logging.class);
	
	  @Pointcut("execution(* com.example.demo.service..*(..))")
	    public void serviceMethods() {
	    }
	  
	  @Around("serviceMethods()")
	    public Object logExecution(ProceedingJoinPoint joinPoint)
	            throws Throwable {

	        String methodName =
	                joinPoint.getSignature().toShortString();

	        long startTime = System.nanoTime();

	        log.info("Started: {}", methodName);

	        try {
	            Object result = joinPoint.proceed();

	            long durationMs =
	                    (System.nanoTime() - startTime) / 1_000_000;

	            log.info("Completed: {} in {} ms",
	                    methodName, durationMs);

	            return result;

	        } catch (Throwable exception) {

	            long durationMs =
	                    (System.nanoTime() - startTime) / 1_000_000;

	            log.error("Failed: {} after {} ms with {}",
	                    methodName,
	                    durationMs,
	                    exception.getClass().getSimpleName());

	            throw exception;
	        }
	    }
	
}
