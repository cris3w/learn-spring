package com.examples.Transactional.application.service;

import com.examples.Application;
import com.examples.Transactional.domain.EmailToken;
import com.examples.Transactional.domain.User;
import com.examples.Transactional.domain.UserId;
import com.examples.Transactional.infrastructure.configuration.AsyncEventsConfig;
import com.examples.Transactional.infrastructure.configuration.H2JpaConfig;
import com.examples.Transactional.infrastructure.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class, AsyncEventsConfig.class})
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Test
    public void WhenCreateUserThenShouldPersistUserWithToken() {
        final String name = "Ana";
        final String email = "ana@example.com";

        final User user = service.createUser(name, email);

        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());

        await().atMost(5, SECONDS)
                .until(() -> userTokenIsPersisted(user.getId()));
    }

    private boolean userTokenIsPersisted(UserId id) {
        final Optional<User> maybeCustomer = repository.findById(id);
        final String token = maybeCustomer.map(User::getToken).map(EmailToken::asString).orElse(null);
        System.out.println(String.format("Checking token... %s", token));
        return maybeCustomer.map(User::hasToken).orElse(false);
    }
}
