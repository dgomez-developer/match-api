package com.example.workshop.kotlinserver.data.repository

import com.example.workshop.kotlinserver.data.model.Player
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * @author Madrid Tech Lab on 2019-12-19.
 */
@Repository
@Profile("database")
interface PlayersMongoRepository: MongoRepository<Player, String>