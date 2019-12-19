package com.example.workshop.kotlinserver.data.repository

import com.example.workshop.kotlinserver.data.model.Player
import org.springframework.data.repository.CrudRepository

/**
 * @author Madrid Tech Lab on 2019-12-19.
 */
interface PlayersRepository: CrudRepository<Player, String>