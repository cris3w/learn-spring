package com.examples.Transactional.domain;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class User {

    @EmbeddedId
    private UserId id;

    private String name;

    private String email;

    @Embedded
    private EmailToken token;

    private User() {}

    public User(UserId id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void generateToken() {
        token = new EmailToken();
    }

    public boolean hasToken() {
        return Optional.ofNullable(token).map(token -> !StringUtils.isEmpty(token)).orElse(false);
    }

    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public EmailToken getToken() {
        return token;
    }
}
