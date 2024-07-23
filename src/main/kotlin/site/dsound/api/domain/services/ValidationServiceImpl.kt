package site.dsound.api.domain.services

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.stereotype.Service

@Service
class ValidationServiceImpl(private val validator: Validator) : ValidationService {
    override suspend fun <T> validateObject(`object`: T): T {
        val constraintViolations = validator.validate(`object`)
        if (constraintViolations.isNotEmpty())
            throw ConstraintViolationException(constraintViolations)
        return `object`
    }
}