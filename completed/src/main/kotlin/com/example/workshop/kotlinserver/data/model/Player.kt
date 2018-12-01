package com.example.workshop.kotlinserver.data.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * @author Madrid Tech Lab on 10/11/2018.
 */
@Entity
data class Player(@Id @GeneratedValue(generator="system-uuid")
                  @GenericGenerator(name="system-uuid", strategy = "uuid")
                  val id: String,
                  val name: String,
                  val score: Int)