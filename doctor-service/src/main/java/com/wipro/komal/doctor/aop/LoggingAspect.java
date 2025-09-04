package com.wipro.komal.doctor.aop;
import lombok.extern.slf4j.Slf4j; import org.aspectj.lang.ProceedingJoinPoint; import org.aspectj.lang.annotation.*; import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect @Component @Slf4j
public class LoggingAspect {
  @Around("within(@org.springframework.web.bind.annotation.RestController *)")
  public Object log(ProceedingJoinPoint pjp) throws Throwable{
    long s=System.currentTimeMillis();
    log.info("→ {} args={}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));
    try{ Object r=pjp.proceed(); 
    log.info("← {} returned in {}ms", pjp.getSignature(), System.currentTimeMillis()-s); return r; } 
    catch(Throwable t){ log.error("✖ {} threw {}", pjp.getSignature(), t.toString()); throw t; }
  }
}
