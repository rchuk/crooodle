package org.ukma.spring.crooodle.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Aspect for handling exceptions thrown by methods in the service package.
 * Logs detailed exception information, writes to a log file, and sends notifications.
 */
@Aspect
@Component
public class ExceptionHandlingAspect {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    /**
     * Method to log exceptions thrown by methods in the service package.
     *
     * @param joinPoint the join point providing reflective access to the method
     * @param ex the exception thrown
     */
    @AfterThrowing(pointcut = "execution(* org.ukma.spring.crooodle.service..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                params.append(arg).append(", ");
            }
        }
        log.error("Exception in method {} with parameters [{}]: {}", methodName, params.toString(), ex.getMessage(), ex);

        // Write exception details to a log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("exceptions.log", true))) {
            writer.write("[" + LocalDateTime.now() + "] Exception in method " + methodName + " with parameters [" + params.toString() + "]: " + ex.getMessage());
            writer.newLine();
        } catch (IOException e) {
            log.error("Failed to write exception to file: {}", e.getMessage(), e);
        }

        // Send a notification (placeholder logic)
        sendNotification(methodName, ex);
    }

    /**
     * Sends a notification when an exception occurs. This is a placeholder for integrating with actual notification systems.
     *
     * @param methodName the name of the method where the exception occurred
     * @param ex the exception thrown
     */
    private void sendNotification(String methodName, Exception ex) {
        log.info("Notification sent for exception in method {}: {}", methodName, ex.getMessage());
    }
}