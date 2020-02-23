package com.examples.Transactional.domain;

import java.io.Serializable;
import java.util.UUID;

public class UserId implements Serializable {

    private String id;

    public UserId() {
        this.id = UUID.randomUUID().toString();
    }

    public String asString() {
        return id;
    }
}
