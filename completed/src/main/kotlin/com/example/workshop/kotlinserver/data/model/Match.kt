package com.example.workshop.kotlinserver.data.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.*

/**
 * @author Madrid Tech Lab on 10/11/2018.
 */
@Entity
@Document("match")
data class Match(@Id
                 val id: String = UUID.randomUUID().toString(),
                 @OneToOne(cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.EAGER)
                 val player1: Player,
                 @OneToOne(cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.EAGER)
                 val player2: Player)