package com.example.workshop.kotlinserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinServerApplication

fun main(args: Array<String>) {
    runApplication<KotlinServerApplication>(*args)
}
