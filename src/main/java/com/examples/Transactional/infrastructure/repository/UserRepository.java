package com.examples.Transactional.infrastructure.repository;

import com.examples.Transactional.domain.User;
import com.examples.Transactional.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {}
