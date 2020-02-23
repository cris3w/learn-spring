package com.examples.Transactional.application.listener;

import com.examples.Transactional.application.exception.UserNotFoundException;
import com.examples.Transactional.domain.User;
import com.examples.Transactional.domain.UserCreated;
import com.examples.Transactional.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
public class UserCreatedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreatedListener.class);

    private final UserRepository repository;

    public UserCreatedListener(UserRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener(UserCreated.class)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onUserCreated(UserCreated userCreated) {
        LOGGER.info("onUserCreated Thread {}", Thread.currentThread().getName());
        Optional<User> maybeUser = repository.findById(userCreated.getId());
        if (!maybeUser.isPresent()) throw new UserNotFoundException();
        maybeUser.ifPresent(User::generateToken);
        LOGGER.info("Token generated");
    }
}
