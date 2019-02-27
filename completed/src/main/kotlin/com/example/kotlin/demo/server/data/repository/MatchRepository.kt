package com.example.kotlin.demo.server.data.repository

import com.example.kotlin.demo.server.data.model.Match
import org.springframework.data.repository.CrudRepository

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
interface MatchRepository : CrudRepository<Match, String>