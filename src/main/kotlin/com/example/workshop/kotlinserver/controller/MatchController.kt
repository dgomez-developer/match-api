package com.example.workshop.kotlinserver.controller

import com.example.workshop.kotlinserver.controller.model.MatchParam
import com.example.workshop.kotlinserver.controller.model.PlayerParam
import com.example.workshop.kotlinserver.data.model.Match
import com.example.workshop.kotlinserver.data.model.Player
import com.example.workshop.kotlinserver.data.repository.MatchJpaRepository
import com.example.workshop.kotlinserver.data.repository.MatchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


/**
 * @author Madrid Tech Lab on 06/11/2018.
 */
@RestController
class MatchController {

    @Autowired
    lateinit var matchRepository: MatchRepository

    @RequestMapping(method = [RequestMethod.GET], value = ["/matches"])
    fun getAllMatches(): List<MatchParam> {
        return matchRepository.findAll().map {
            MatchParam(it.id,
                    PlayerParam(it.player1.id, it.player1.name, it.player1.score),
                    PlayerParam(it.player2.id, it.player2.name, it.player2.score)) }
    }

    @RequestMapping(method = [RequestMethod.DELETE], value = ["/match/{matchId}"])
    fun deleteMatch(@PathVariable("matchId", required = true) id: String, response: HttpServletResponse): String {
        matchRepository.deleteById(id)
        response.status = HttpStatus.NO_CONTENT.value()
        return ""
    }

    @RequestMapping(method = [RequestMethod.POST], value = ["/match"])
    fun createMatch(@RequestBody match: MatchParam, response: HttpServletResponse): MatchParam {
        val result = matchRepository.save(Match("",
                player1 = Player("",name = match.player1.name, score = match.player1.score),
                player2 = Player("",name = match.player2.name, score = match.player2.score)))
        response.status = HttpStatus.CREATED.value()
        return MatchParam(result.id,
                PlayerParam(result.player1.id,result.player1.name, result.player1.score),
                PlayerParam(result.player2.id, result.player2.name, result.player2.score))
    }

}