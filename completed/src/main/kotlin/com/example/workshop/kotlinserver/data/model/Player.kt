package com.example.workshop.kotlinserver.data.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

/**
 * @author Madrid Tech Lab on 10/11/2018.
 */
@Entity
@Document("player")
data class Player(@Id
                  val id: String = UUID.randomUUID().toString(),
                  val name: String,
                  val score: Int)