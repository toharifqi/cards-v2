package com.exercise.cards.controller

import com.exercise.cards.constant.CardConstant
import com.exercise.cards.dto.Card
import com.exercise.cards.dto.ErrorResponse
import com.exercise.cards.dto.ProjectContactInfo
import com.exercise.cards.dto.Response
import com.exercise.cards.service.CardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "CRUD REST APIs for Cards in X Bank",
    description = "CRUD REST APIs in X Bank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class CardController(
    private val cardService: CardService,
    private val environment: Environment,
    private val projectContactInfo: ProjectContactInfo,
) {

    @Value("\${build.version}")
    val buildVersion: String? = null

    @Operation(
        summary = "Create Card REST API",
        description = "REST API to create new Card inside EazyBank"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @PostMapping("/create")
    fun create(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Response> {
        cardService.createCard(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                Response(
                    statusCode = CardConstant.STATUS_201,
                    statusMessage = CardConstant.MESSAGE_201
                )
            )
    }

    @Operation(
        summary = "Fetch Card Details REST API",
        description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(
                    implementation = ErrorResponse::class
                )
            )]
        )
    )
    @GetMapping("/fetch")
    fun fetch(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Card> {
        val loan = cardService.fetchCardInfo(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loan)
    }

    @Operation(
        summary = "Update Card Details REST API",
        description = "REST API to update card details based on a card number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(
                    implementation = ErrorResponse::class
                )
            )]
        )
    )
    @PutMapping("/update")
    fun update(@RequestBody @Valid card: Card): ResponseEntity<Response> {
        val isUpdated = cardService.updateCard(card)

        return if (isUpdated) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = CardConstant.STATUS_200,
                        statusMessage = CardConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = CardConstant.STATUS_417,
                        statusMessage = CardConstant.MESSAGE_417_UPDATE
                    )
                )
        }
    }

    @Operation(
        summary = "Delete Card Details REST API",
        description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "417",
            description = "Expectation Failed"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(
                    implementation = ErrorResponse::class
                )
            )]
        )
    )
    @DeleteMapping("/delete")
    fun delete(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{12})", message = "Mobile Number must be 12 digits of number")
        mobileNumber: String
    ): ResponseEntity<Response> {
        val isDeleted = cardService.deleteCard(mobileNumber)
        return if (isDeleted) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    Response(
                        statusCode = CardConstant.STATUS_200,
                        statusMessage = CardConstant.MESSAGE_200
                    )
                )
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    Response(
                        statusCode = CardConstant.STATUS_417,
                        statusMessage = CardConstant.MESSAGE_417_UPDATE
                    )
                )
        }
    }

    @Operation(
        summary = "Get Build Information",
        description = "Get Build information that is deployed into cards microservice"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/build-info")
    fun getBuildInfo(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion)
    }

    @Operation(
        summary = "Get Java version",
        description = "Get Java version that is installed into cards microservice"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/java-version")
    fun getJavaVersion(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME") ?: "null, need permission")
    }

    @Operation(
        summary = "Get Contact Info",
        description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = [Content(
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/contact-info")
    fun getContactInfo(): ResponseEntity<ProjectContactInfo> {
        return ResponseEntity.status(HttpStatus.OK).body(projectContactInfo)
    }
}
