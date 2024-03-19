package com.exercise.cards

import com.exercise.cards.dto.ProjectContactInfo
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@OpenAPIDefinition(
	info = Info(
		title = "Card microservice REST API Documentation",
		description = "Card Microservice is a restful HTTP Api service to handle bank user's Card business logic such as create new card, fetch card info, update card limit, and delete card.",
		version = "v1",
		contact = Contact(
			name = "Rifqi Naufal Tohari",
			email = "rifqinaufaltohari@gmail.com",
			url = "https://www.linkedin.com/in/rifqi-naufal-tohari/"
		)
	),
	externalDocs = ExternalDocumentation(
		description =  "Source Code",
		url = "https://github.com/toharifqi/cards"
	)
)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = [ProjectContactInfo::class])
@SpringBootApplication
class CardsApplication

fun main(args: Array<String>) {
	runApplication<CardsApplication>(*args)
}
