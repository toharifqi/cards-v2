package com.exercise.cards.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    resourceName: String,
    fieldName: String,
    fieldValue: String,
) : RuntimeException() {
    val responseMessage: String = "$resourceName is not found with the given input data $fieldName: $fieldValue"
} 
