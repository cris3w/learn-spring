package com.examples.SpringRetry;

import com.examples.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RetryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryTest.class);

    @Autowired
    private RetryTemplate retryTemplate;

    @Autowired
    NotificationService notificationService;

    private String fromError = "from error";

    @Test
    public void WhenSendEmailThenRetryUntilIsSent() throws Throwable {
        String response;
        try {
            response = retry(2);
        } catch (Throwable throwable) {
            response = fromError;
        }
        assertEquals(response, "SENT");
    }

    @Test // The maxAttemps are defined by config
    public void WhenSendEmailThenRetryUntilMaxAttemps() {
        String response;
        try {
            response = retry(7);
        } catch (Throwable throwable) {
            response = fromError;
        }
        assertEquals(response, fromError);
    }

    private String retry(int numberOfRetries) throws Throwable {
        notificationService.resetTimes();
        return retryTemplate.execute(retryContext -> {
            LOGGER.info(String.format("Retry number %s at %s",
                    retryContext.getRetryCount(),
                    LocalTime.now()));
            return notificationService.send(numberOfRetries);
        });
    }
}
