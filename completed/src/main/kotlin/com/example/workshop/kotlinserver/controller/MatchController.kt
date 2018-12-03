package com.example.workshop.kotlinserver.controller

import com.example.workshop.kotlinserver.controller.model.Match
import com.example.workshop.kotlinserver.controller.model.Player
import com.example.workshop.kotlinserver.data.repository.MatchRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


/**
 * @author Madrid Tech Lab on 06/11/2018.
 */
@RestController
class MatchController {

    val matchRepository = MatchRepository()

    @RequestMapping(method = [RequestMethod.GET], value = ["/matches"])
    fun getAllMatches(): List<Match> {
        return matchRepository.getMatches()
    }

    @PostMapping(value = ["/match"])
    fun createMatch(@RequestBody match: Match, response: HttpServletResponse): Match {
        matchRepository.save(Match(Player(match.player1.name, match.player1.score),
                Player(match.player2.name, match.player2.score)))
        response.status = HttpStatus.CREATED.value()
        return match
    }

}