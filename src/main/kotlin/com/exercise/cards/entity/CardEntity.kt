package com.exercise.cards.entity

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity(name = "cards")
@EntityListeners(AuditingEntityListener::class)
data class CardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    val cardId: Long = 0,

    val mobileNumber: String,

    val cardNumber: String,

    val cardType: String,

    val totalLimit: Int,

    val amountUsed: Int,

    val availableAmount: Int,

    @CreatedDate
    override var createdAt: LocalDateTime? = null,

    @CreatedBy
    override var createdBy: String? = null,

    @LastModifiedDate
    override var updatedAt: LocalDateTime? = null,

    @LastModifiedBy
    override var updatedBy: String? = null,

) : BaseEntity
