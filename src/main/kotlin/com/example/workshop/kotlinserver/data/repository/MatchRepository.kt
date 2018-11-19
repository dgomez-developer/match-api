package com.example.workshop.kotlinserver.data.repository

import com.example.workshop.kotlinserver.data.model.Match
import org.springframework.data.repository.Repository

/**
 * @author Madrid Tech Lab on 12/11/2018.
 */
interface MatchRepository : Repository<Match, String> {

    fun findAll(): List<Match>

    fun deleteById(id: String)

    fun save(match: Match): Match
}