package com.example.workshop.kotlinserver.controller

import com.example.workshop.kotlinserver.controller.model.Match
import com.example.workshop.kotlinserver.controller.model.Player
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

/**
 * @author Madrid Tech Lab on 06/11/2018.
 */
@RestController
class MatchController {

    var matches: MutableList<Match> = mutableListOf()

    @RequestMapping(method = [RequestMethod.GET], value = ["/matches"])
    fun getAllMatches(): List<Match> {
        return matches
    }

    @PostMapping(value = ["/match"])
    fun createMatch(@RequestBody match: Match, response: HttpServletResponse): Match {
        matches.add(match)
        response.status = HttpStatus.CREATED.value()
        return match
    }
}