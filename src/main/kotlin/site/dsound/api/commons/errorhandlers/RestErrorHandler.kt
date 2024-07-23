package site.dsound.api.commons.errorhandlers

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import site.dsound.api.application.dtos.MetaResponse
import site.dsound.api.application.dtos.WebResponse

@RestControllerAdvice
class RestErrorHandler {
    private val log = LoggerFactory.getLogger(RestErrorHandler::class.java)

    @ExceptionHandler(ConstraintViolationException::class)
    suspend fun constraintViolationException(constraintViolationException: ConstraintViolationException): ResponseEntity<WebResponse<String>> {
        log.warn("Error", constraintViolationException)
        val message = java.lang.String.join(
            ", ",
            constraintViolationException.constraintViolations.stream()
                .map { obj: ConstraintViolation<*> -> obj.message }
                .toList())
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                WebResponse(
                    data = null,
                    meta = MetaResponse(code = HttpStatus.BAD_REQUEST.value().toString(), message = message)
                )
            )
    }

}