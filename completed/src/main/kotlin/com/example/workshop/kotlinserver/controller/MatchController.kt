package com.example.workshop.kotlinserver.controller

import com.example.workshop.kotlinserver.controller.model.Match
import com.example.workshop.kotlinserver.controller.model.Player
import org.springframework.web.bind.annotation.*

/**
 * @author Madrid Tech Lab on 06/11/2018.
 */
@RestController
class MatchController {

    @RequestMapping(method = [RequestMethod.GET], value = ["/matches"])
    fun getAllMatches(): List<Match> {

        val player1 = Player("Victor", 10)
        val player2 = Player("Debora", 15)
        val match1 = Match(player1, player2)

        val player3 = Player("Pablo", 15)
        val player4 = Player("Sergio", 13)
        val match2 = Match(player3, player4)

        return arrayListOf(match1, match2)
    }

}