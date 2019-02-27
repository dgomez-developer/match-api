package com.example.kotlin.demo.server.controller

import com.example.kotlin.demo.server.data.repository.MatchRepository
import com.example.kotlin.demo.server.data.model.Match
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
@RestController
class MatchController {

    @Autowired
    lateinit var matchRepository: MatchRepository

    @RequestMapping(method = [RequestMethod.GET], value = ["/matches"])
    fun getAllMatches(): List<Match> {
        return matchRepository.findAll().toList()
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/match"])
    fun createMatch(@RequestBody body: Match, response: HttpServletResponse): Match {
        val createdMatch = matchRepository.save(body)
        response.status = HttpStatus.CREATED.value()
        return createdMatch
    }

}