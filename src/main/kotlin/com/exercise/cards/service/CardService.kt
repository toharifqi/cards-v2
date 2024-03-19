package com.exercise.cards.service

import com.exercise.cards.constant.CardConstant
import com.exercise.cards.dto.Card
import com.exercise.cards.entity.CardEntity
import com.exercise.cards.exception.CardAlreadyExistException
import com.exercise.cards.exception.ResourceNotFoundException
import com.exercise.cards.repository.CardRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

interface CardService {
    fun createCard(mobileNumber: String)

    fun fetchCardInfo(mobileNumber: String): Card

    fun updateCard(card: Card): Boolean

    fun deleteCard(mobileNumber: String): Boolean
}

@Service
class CardServiceImpl(
    private val cardRepository: CardRepository
) : CardService {
    override fun createCard(mobileNumber: String) {
        checkIfCardAlreadyExist(mobileNumber)

        cardRepository.save(generateCard(mobileNumber))
    }

    private fun generateCard(mobileNumber: String): CardEntity {
        val randomCardNumber = 100000000000L + Random.nextInt(900000000)

        return CardEntity(
            mobileNumber = mobileNumber,
            cardNumber = randomCardNumber.toString(),
            cardType = CardConstant.CREDIT_CARD,
            totalLimit = CardConstant.NEW_CARD_LIMIT,
            amountUsed = 0,
            availableAmount = CardConstant.NEW_CARD_LIMIT,
        )
    }

    private fun checkIfCardAlreadyExist(mobileNumber: String) {
        val optionalCard = cardRepository.findByMobileNumber(mobileNumber)
        if (optionalCard.isPresent) {
            throw CardAlreadyExistException("Card already registered with given mobileNumber $mobileNumber")
        }
    }

    override fun fetchCardInfo(mobileNumber: String): Card {
        val cardEntity = cardRepository.findByMobileNumber(mobileNumber).orElseThrow {
            throw ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        }

        return Card(cardEntity)
    }

    override fun updateCard(card: Card): Boolean {
        val cardEntity = cardRepository.findByMobileNumber(card.mobileNumber).orElseThrow {
            throw ResourceNotFoundException("Card", "mobileNumber", card.mobileNumber)
        }.copy(
            mobileNumber = card.mobileNumber,
            cardNumber = card.cardNumber,
            cardType = card.cardType,
            totalLimit = card.totalLimit,
            amountUsed = card.amountUsed,
            availableAmount = card.availableAmount,
        )
        
        cardRepository.save(cardEntity)
        
        return true
    }

    override fun deleteCard(mobileNumber: String): Boolean {
        val cardEntity = cardRepository.findByMobileNumber(mobileNumber).orElseThrow {
            throw ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        }
        
        cardRepository.deleteById(cardEntity.cardId)
        
        return true
    }

}
