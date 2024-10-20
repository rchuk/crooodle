package org.ukma.spring.crooodle.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.io.FileWriter;
import java.io.IOException;

public class CustomFileAppender extends AppenderBase<ILoggingEvent> {

    private FileWriter fileWriter;

    @Override
    public void start() {
        try {
            String logFilePath = "logs/custom_appender.log";
            this.fileWriter = new FileWriter(logFilePath, true);
            super.start();
        } catch (IOException e) {
            addError("Failed to open log file", e);
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        try {
            fileWriter.write(event.getFormattedMessage() + "\n");
        } catch (IOException e) {
            addError("Failed to write to log file", e);
        }
    }

    @Override
    public void stop() {
        super.stop();
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            addError("Failed to close log file", e);
        }
    }
}