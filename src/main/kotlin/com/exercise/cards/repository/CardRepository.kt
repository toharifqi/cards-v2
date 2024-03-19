package com.exercise.cards.repository

import com.exercise.cards.entity.CardEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CardRepository: JpaRepository<CardEntity, Long> {
    fun findByMobileNumber(mobileNumber: String): Optional<CardEntity>
}
