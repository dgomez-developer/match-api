package com.example.kotlin.demo.server.data.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

/**
 * @author Madrid Tech Lab on 2019-02-20.
 */
@Entity
@Document("player")
class Player(
        @Id
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val score: Int)