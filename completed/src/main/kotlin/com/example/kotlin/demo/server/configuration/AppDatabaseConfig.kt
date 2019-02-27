package com.example.kotlin.demo.server.configuration

import com.example.kotlin.demo.server.data.repository.MatchMongoRepository
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = [MatchMongoRepository::class])
@Profile("database")
class AppDatabaseConfig