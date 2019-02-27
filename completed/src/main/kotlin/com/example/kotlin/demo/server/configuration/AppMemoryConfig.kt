package com.example.kotlin.demo.server.configuration

import com.example.kotlin.demo.server.data.repository.MatchJpaRepository
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
@Configuration
@Profile("local")
@EnableJpaRepositories(basePackageClasses = [MatchJpaRepository::class])
class AppMemoryConfig