package com.example.workshop.kotlinserver.data.repository

import com.example.workshop.kotlinserver.data.model.Match
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * @author Madrid Tech Lab on 11/11/2018.
 */
@Repository
@Profile("local")
interface MatchJpaRepository : JpaRepository<Match, String>, MatchRepository