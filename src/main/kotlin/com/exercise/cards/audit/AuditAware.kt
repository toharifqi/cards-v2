package com.exercise.cards.audit

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

@Component("auditAwareImpl")
class AuditAware: AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        return Optional.of("CARDS_MS")
    }

}
