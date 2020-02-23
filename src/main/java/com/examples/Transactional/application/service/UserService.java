package com.examples.Transactional.application.service;

import com.examples.Transactional.domain.User;
import com.examples.Transactional.domain.UserCreated;
import com.examples.Transactional.domain.UserId;
import com.examples.Transactional.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    // https://blog.pragmatists.com/spring-events-and-transactions-be-cautious-bdb64cb49a95

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final ApplicationEventPublisher publisher;

    public UserService(UserRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public User createUser(String name, String email) {
        try {
            LOGGER.info("Creating user Thread {}", Thread.currentThread().getName());
            final User user = repository.save(new User(new UserId(), name, email));
            publisher.publishEvent(new UserCreated(user.getId()));
            LOGGER.info("User {} created", user.getEmail());
            return user;
        } catch (Exception exception) {
            LOGGER.error("Error when creating user {}", email);
            throw exception;
        }
    }
}
