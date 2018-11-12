package com.example.workshop.kotlinserver.data.repository

import com.example.workshop.kotlinserver.data.model.Match
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author Madrid Tech Lab on 11/11/2018.
 */
@Repository
interface MatchRepository : CrudRepository<Match, String>