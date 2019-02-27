package com.example.kotlin.demo.server.data.repository

import com.example.kotlin.demo.server.data.model.Match
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
@Repository
@Profile("database")
interface MatchMongoRepository: MongoRepository<Match, String>, MatchRepository