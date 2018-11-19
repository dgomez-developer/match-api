package com.example.workshop.kotlinserver.data.repository

import com.example.workshop.kotlinserver.data.model.Match
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * @author Madrid Tech Lab on 12/11/2018.
 */
@Repository
@Profile("database")
interface MatchMongoRepository: MongoRepository<Match, String>, MatchRepository