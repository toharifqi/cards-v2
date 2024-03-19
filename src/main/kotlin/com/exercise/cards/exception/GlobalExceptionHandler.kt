package com.exercise.cards.exception

import com.exercise.cards.dto.ErrorResponse
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val validationErrors = mutableMapOf<String, String>()
        validationErrors["errorCode"] = HttpStatus.INTERNAL_SERVER_ERROR.toString()
        val errorlist = ex.bindingResult.allErrors

        errorlist.forEach { objectError ->
            val fieldName = (objectError.arguments?.get(0) as DefaultMessageSourceResolvable).code ?: ""
            validationErrors[fieldName] = objectError.defaultMessage.toString()
        }

        return ResponseEntity(validationErrors, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        exception: Exception,
        webRequest: WebRequest,
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            webRequest.getDescription(false),
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception.message ?: "",
            LocalDateTime.now()
        )

        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(CardAlreadyExistException::class)
    fun handleLoanAlreadyExistException(
        exception: CardAlreadyExistException,
        webRequest: WebRequest,
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST,
            exception.responseMessage,
            LocalDateTime.now()
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundExceptionException(
        exception: ResourceNotFoundException,
        webRequest: WebRequest,
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            webRequest.getDescription(false),
            HttpStatus.NOT_FOUND,
            exception.responseMessage,
            LocalDateTime.now()
        )

        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }
}
