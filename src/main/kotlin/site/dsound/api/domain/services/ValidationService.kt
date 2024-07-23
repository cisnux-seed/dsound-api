package site.dsound.api.domain.services

interface ValidationService {
    suspend fun <T> validateObject(`object`: T): T
}