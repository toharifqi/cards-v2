package com.exercise.cards.dto

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cards")
data class ProjectContactInfo (
    val message: String,
    val contactDetails: Map<String, String>,
    val onCallSupport: List<String>
)
