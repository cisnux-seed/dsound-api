package site.dsound.api.domain.services

import site.dsound.api.domain.entities.AuthorizeRedirectUri
import site.dsound.api.domain.entities.Token

interface AuthenticationService {
    suspend fun authorize(): AuthorizeRedirectUri
    suspend fun getToken(code: String, codeVerifier: String): Token
    suspend fun refreshToken(refreshToken: String): Token
}