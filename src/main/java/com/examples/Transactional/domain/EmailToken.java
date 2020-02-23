package com.examples.Transactional.domain;

import java.util.UUID;

public class EmailToken {

    private final String token;

    EmailToken() {
        token = UUID.randomUUID().toString();
    }

    public String asString() {
        return token;
    }
}
