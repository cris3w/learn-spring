package com.examples.Transactional.domain;

public class UserCreated {

    private UserId id;

    public UserCreated(UserId id) {
        this.id = id;
    }

    public UserId getId() {
        return id;
    }
}
