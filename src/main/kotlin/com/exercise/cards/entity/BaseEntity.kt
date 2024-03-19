package com.exercise.cards.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
interface BaseEntity{
    @get:Column(updatable = false)
    var createdAt: LocalDateTime?

    @get:Column(updatable = false)
    var createdBy: String?

    @get:Column(insertable = false)
    var updatedAt: LocalDateTime?

    @get:Column(insertable = false)
    var updatedBy: String?
}
