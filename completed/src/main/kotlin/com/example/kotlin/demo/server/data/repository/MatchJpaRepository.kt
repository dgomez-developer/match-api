package com.example.kotlin.demo.server.data.repository

import com.example.kotlin.demo.server.data.model.Match
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
@Repository
@Profile("local")
interface MatchJpaRepository : JpaRepository<Match, String>, MatchRepository