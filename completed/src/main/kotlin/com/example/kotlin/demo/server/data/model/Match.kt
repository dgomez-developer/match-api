package com.example.kotlin.demo.server.data.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.*

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
@Entity
@Document("match")
data class Match(
        @Id
        val id: String = UUID.randomUUID().toString(),
        @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
        val player1: Player,
        @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
        val player2: Player)