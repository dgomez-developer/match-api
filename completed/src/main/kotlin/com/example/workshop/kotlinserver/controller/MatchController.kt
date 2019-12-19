package com.example.workshop.kotlinserver.controller

import com.example.workshop.kotlinserver.controller.model.MatchParam
import com.example.workshop.kotlinserver.controller.model.PlayerParam
import com.example.workshop.kotlinserver.data.model.Match
import com.example.workshop.kotlinserver.data.model.Player
import com.example.workshop.kotlinserver.data.repository.MatchRepository
import com.example.workshop.kotlinserver.data.repository.PlayersRepository
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    lateinit var usersRepository: PlayersRepository

    @CrossOrigin
    @RequestMapping(method = [RequestMethod.GET], value = ["/matches"])
    fun getAllMatches(): List<MatchParam> {
        return matchRepository.findAll().map {
            MatchParam(it.id,
                    PlayerParam(it.player1.id, it.player1.name, it.player1.score),
                    PlayerParam(it.player2.id, it.player2.name, it.player2.score))
        }
    }

    @CrossOrigin
    @RequestMapping(method = [RequestMethod.DELETE], value = ["/match/{matchId}"])
    fun deleteMatch(@PathVariable("matchId", required = true) id: String, response: HttpServletResponse): String {
        matchRepository.deleteById(id)
        response.status = HttpStatus.NO_CONTENT.value()
        return ""
    }

    @CrossOrigin
    @RequestMapping(method = [RequestMethod.POST], value = ["/match"])
    fun createMatch(@RequestBody match: MatchParam, response: HttpServletResponse): MatchParam {
        val result = matchRepository.save(Match(
                player1 = Player(id = match.player1.id!!, name = match.player1.name, score = match.player1.score),
                player2 = Player(id = match.player2.id!!, name = match.player2.name, score = match.player2.score)))
        response.status = HttpStatus.CREATED.value()
        return MatchParam(result.id,
                PlayerParam(result.player1.id, result.player1.name, result.player1.score),
                PlayerParam(result.player2.id, result.player2.name, result.player2.score))
    }

    @CrossOrigin
    @RequestMapping(method = [RequestMethod.POST], value = ["/player"])
    fun upsertPlayer(@RequestBody playerParam: PlayerParam): PlayerParam {
        return if (playerParam.id.isNullOrEmpty()) {
            val result = usersRepository.save(Player(name = playerParam.name, score = playerParam.score))
            PlayerParam(id = result.id, name = result.name, score = result.score)
        } else {
            val result = usersRepository.save(Player(id = playerParam.id!!, name = playerParam.name, score = playerParam.score))
            PlayerParam(id = result.id, name = result.name, score = result.score)
        }
    }

    @CrossOrigin
    @RequestMapping(method = [RequestMethod.PUT], value = ["/player/{id}"])
    fun updatePlayer(@RequestBody playerParam: PlayerParam, @PathVariable("id") id: String): PlayerParam {
        val result = usersRepository.save(Player(id = playerParam.id!!, name = playerParam.name, score = playerParam.score))
        return PlayerParam(id = result.id, name = result.name, score = result.score)
    }

    @CrossOrigin
    @RequestMapping(method = [RequestMethod.GET], value = ["/players"])
    fun getPlayers(@RequestBody playerParam: PlayerParam): List<PlayerParam> {
        return usersRepository.findAll().map { PlayerParam(it.id, it.name, it.score) }
    }
}