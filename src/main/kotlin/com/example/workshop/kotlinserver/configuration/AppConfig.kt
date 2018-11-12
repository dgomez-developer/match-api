package com.example.workshop.kotlinserver.configuration

import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean


/**
 * @author Madrid Tech Lab on 11/11/2018.
 */
@Configuration
class AppConfig {

    @Bean
    fun includeTransientObjectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule())
        return mapper
    }
}