package com.example.workshop.kotlinserver.configuration

import com.example.workshop.kotlinserver.data.repository.MatchJpaRepository
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


/**
 * @author Madrid Tech Lab on 11/11/2018.
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = [MatchJpaRepository::class])
@Profile("local")
class AppLocalConfig {

    @Bean
    fun includeTransientObjectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule())
        return mapper
    }
}