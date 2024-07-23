package site.dsound.api.domain.services

import site.dsound.api.domain.entities.AuthorizeRedirectUri

interface AuthenticationService {
    suspend fun authorize(): AuthorizeRedirectUri
}