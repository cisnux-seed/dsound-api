package site.dsound.api.domain.repositories

import site.dsound.api.domain.entities.Token

interface AuthenticationRepository {
    suspend fun getToken(code: String, codeVerifier: String): Token
    suspend fun refreshToken(refreshToken: String): Token
}