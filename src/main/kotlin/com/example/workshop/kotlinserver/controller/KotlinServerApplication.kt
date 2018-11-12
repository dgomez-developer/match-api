package com.example.workshop.kotlinserver.controller

import com.example.workshop.kotlinserver.data.repository.MatchRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = [MatchRepository::class])
@ComponentScan(basePackages = ["com.example.workshop.kotlinserver.*" ])
@EntityScan("com.example.workshop.kotlinserver.*")
class KotlinServerApplication

fun main(args: Array<String>) {
    runApplication<KotlinServerApplication>(*args)
}
