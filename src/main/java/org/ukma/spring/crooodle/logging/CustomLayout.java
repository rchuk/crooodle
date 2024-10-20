package org.ukma.spring.crooodle.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomLayout extends LayoutBase<ILoggingEvent> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String doLayout(ILoggingEvent event) {
        String timestamp = dateFormat.format(new Date(event.getTimeStamp()));

        String threadName = event.getThreadName();

        String level = String.format("%-5s", event.getLevel());

        String loggerName = shortenLoggerName(event.getLoggerName());

        String message = event.getFormattedMessage();

        return String.format("%s [%s] %s %s - %s%n",
                timestamp, threadName, level, loggerName, message);
    }

    private String shortenLoggerName(String loggerName) {
        if (loggerName.length() > 36) {
            return loggerName.substring(0, 36);
        }
        return loggerName;
    }
}