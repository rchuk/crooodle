package org.ukma.spring.crooodle.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.Duration;

/**
 * Aspect for logging the performance of method executions in the service package.
 * Logs execution time and additional method parameter details for better analysis.
 */
@Aspect
@Component
public class PerformanceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(PerformanceLoggingAspect.class);

    /**
     * Logs the execution time of methods and provides warnings if an exception occurs.
     *
     * @param joinPoint the join point providing reflective access to the method
     * @return the result of the method execution
     * @throws Throwable if the method execution throws any exception
     */
    @Around("execution(* org.ukma.spring.crooodle.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = Instant.now().toEpochMilli();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.warn("Method {} threw an exception: {}", joinPoint.getSignature(), e.getMessage());
            throw e;
        }
        long endTime = Instant.now().toEpochMilli();
        long duration = endTime - startTime;

        if (duration > 100) { // Log only if the execution time exceeds 100 ms
            log.info("Execution time of {}: {} ms", joinPoint.getSignature(), duration);
        } else {
            log.debug("Execution time of {}: {} ms (below threshold)", joinPoint.getSignature(), duration);
        }

        // Log method parameters for additional context
        logMethodParameters(joinPoint);
        return result;
    }

    /**
     * Logs the parameters passed to the method for better traceability.
     *
     * @param joinPoint the join point providing access to method arguments
     */
    private void logMethodParameters(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            StringBuilder params = new StringBuilder("Parameters: ");
            for (Object arg : args) {
                params.append(arg).append(", ");
            }
            log.debug("Method {} called with {}", joinPoint.getSignature(), params.toString());
        } else {
            log.debug("Method {} called with no parameters", joinPoint.getSignature());
        }
    }
}
