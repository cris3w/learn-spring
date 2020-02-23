package com.examples.Transactional.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.examples.Transactional.infrastructure.repository")
@EnableTransactionManagement
public class H2JpaConfig {}
