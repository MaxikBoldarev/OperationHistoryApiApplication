package ru.netology.boldarev.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "operation")
public class OperationProperties {

    private long sleepMilliSeconds;

    public long getSleepMilliSeconds() {
        return sleepMilliSeconds;
    }

    public void setSleepMilliSeconds(long sleepMilliSeconds) {
        this.sleepMilliSeconds = sleepMilliSeconds;
    }
}
