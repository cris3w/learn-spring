package com.examples.SpringRetry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private int times = 0;

    public String send(int numberOfRetries) throws Exception {
        LOGGER.info("Trying to send an email");
        if (times < numberOfRetries) {
            times++;
            throw new RuntimeException("Error trying to send an email");
        }
        LOGGER.info("Email sent!");
        return "SENT";
    }

    public void resetTimes() {
        this.times = 0;
    }
}
