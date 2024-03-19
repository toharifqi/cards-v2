package com.exercise.cards.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class CardAlreadyExistException(
    message: String
) : RuntimeException() {
    val responseMessage: String = message
}
