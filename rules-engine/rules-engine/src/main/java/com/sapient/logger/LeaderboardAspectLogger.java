package com.sapient.logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component; 

@Aspect
@Component
public class LeaderboardAspectLogger {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Around("execution(* com.sapient.rulesengine.RulesEngineController.HelloWorld())")
    public Object aroundHelloWorld(ProceedingJoinPoint joinPoint){
        Object returnedVal=null;
        try {
            log.debug("Before");
            returnedVal=joinPoint.proceed();
            log.debug("After");
        } catch (Throwable e) {
            log.error("Error occured");
        }
        return returnedVal;
    }

    
   
    
       
    
}