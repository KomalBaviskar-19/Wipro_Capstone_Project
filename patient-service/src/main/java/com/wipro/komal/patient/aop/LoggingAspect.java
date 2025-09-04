package com.wipro.komal.patient.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void restController(){}

  @Around("restController()")
  public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.currentTimeMillis();
    log.info("→ {} args={}", pjp.getSignature(), Arrays.toString(pjp.getArgs()));
    try {
      Object ret = pjp.proceed();
      log.info("← {} returned in {} ms", pjp.getSignature(), System.currentTimeMillis() - start);
      return ret;
    } catch (Throwable t) {
      log.error("✖ {} threw {}", pjp.getSignature(), t.toString());
      throw t;
    }
  }
}
