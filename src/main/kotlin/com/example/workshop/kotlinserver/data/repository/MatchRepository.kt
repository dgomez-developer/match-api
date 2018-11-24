package com.example.workshop.kotlinserver.data.repository

import com.example.workshop.kotlinserver.controller.model.Match

/**
 * @author Madrid Tech Lab on 12/11/2018.
 */
class MatchRepository {

    private var matches: MutableList<Match> = mutableListOf()

    fun save(match: Match) {
        matches.add(match)
    }

    fun getMatches(): List<Match> {
        return matches
    }
}