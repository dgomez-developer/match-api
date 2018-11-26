package com.example.workshop.kotlinserver.data.model

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*

/**
 * @author Madrid Tech Lab on 10/11/2018.
 */
@Entity
@Document(collection = "match")
data class Match(@Id
                 @GeneratedValue(generator="system-uuid")
                 @GenericGenerator(name="system-uuid", strategy = "uuid")
                 val id: String,
                 @OneToOne(cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.EAGER)
                 val player1: Player,
                 @OneToOne(cascade = [(CascadeType.ALL)], orphanRemoval = true, fetch = FetchType.EAGER)
                 val player2: Player)