package com.example.workshop.kotlinserver.controller

import org.springframework.web.bind.annotation.*

/**
 * @author Madrid Tech Lab on 06/11/2018.
 */
@RestController
class MatchController {

    val greeter = Greeter()

    @RequestMapping(method = [RequestMethod.GET], value = ["/hello"])
    fun helloWorld(): String {
        return greeter.sayHello()
    }
}