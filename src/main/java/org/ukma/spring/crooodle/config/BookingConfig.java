package org.ukma.spring.crooodle.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "booking")
public class BookingConfig {

    private boolean enabled;
    private int maxDays;
    private int minDays;

    // Getters and setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }
}
