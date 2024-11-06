package org.ukma.spring.crooodle.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Aspect for rate limiting method calls in the service package to prevent excessive use.
 * Ensures a specified number of method calls per minute and resets counts periodically.
 */
@Aspect
@Component
public class RateLimitingAspect {

    private static final Logger log = LoggerFactory.getLogger(RateLimitingAspect.class);

    private final ConcurrentHashMap<String, AtomicInteger> methodCallCount = new ConcurrentHashMap<>();
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

    @Value("${rate.limit.calls.per.minute:10}")
    private int maxCallsPerMinute;

    public RateLimitingAspect() {
        taskScheduler.initialize();
    }

    /**
     * Limits the number of method calls for specified methods within a one-minute window.
     *
     * @param joinPoint the join point providing reflective access to the method
     * @return the result of the method execution
     * @throws Throwable if the method execution throws any exception
     */
    @Around("execution(* org.ukma.spring.crooodle.service..*(..))")
    public Object limitMethodCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        methodCallCount.putIfAbsent(methodName, new AtomicInteger(0));
        AtomicInteger currentCount = methodCallCount.get(methodName);

        if (currentCount.incrementAndGet() > maxCallsPerMinute) {
            log.warn("Rate limit exceeded for method: {}. Current count: {}", methodName, currentCount.get());
            throw new RuntimeException("Method call limit exceeded for: " + methodName);
        }

        // Schedule a reset after 1 minute
        ScheduledFuture<?> scheduledReset = taskScheduler.schedule(
                () -> {
                    currentCount.set(0);
                    log.debug("Rate limit reset for method: {}", methodName);
                },
                Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(1))
        );

        log.debug("Method {} called. Current call count: {}", methodName, currentCount.get());
        return joinPoint.proceed();
    }

    /**
     * Logs the parameters passed to the method for additional context.
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
            log.debug("Method {} called with parameters: {}", joinPoint.getSignature(), params.toString());
        } else {
            log.debug("Method {} called with no parameters", joinPoint.getSignature());
        }
    }
}
