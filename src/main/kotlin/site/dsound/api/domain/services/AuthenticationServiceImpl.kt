package site.dsound.api.domain.services

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import site.dsound.api.commons.configs.SpotifyProperties
import site.dsound.api.domain.dtos.TokenParams
import site.dsound.api.domain.entities.AuthorizeRedirectUri
import site.dsound.api.domain.entities.Token
import site.dsound.api.domain.repositories.AuthenticationRepository
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

@Service
@RequiredArgsConstructor
class AuthenticationServiceImp(
    private val spotifyConfig: SpotifyProperties,
    private val validationService: ValidationService,
    private val authenticationRepository: AuthenticationRepository
) : AuthenticationService {

    override suspend fun authorize(): AuthorizeRedirectUri {
        val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = SecureRandom()
        val randomString = StringBuilder(64).apply {
            repeat(64) {
                append(possible[random.nextInt(possible.length)])
            }
        }.toString()
        val codeVerifier = randomString

        val hashed = MessageDigest.getInstance("SHA-256")
            .digest(codeVerifier.toByteArray())
        val codeChallengeBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString(hashed)

        return AuthorizeRedirectUri(
            clientId = spotifyConfig.clientId,
            scope = spotifyConfig.scope,
            codeChallenge = codeChallengeBase64,
            codeChallengeMethod = spotifyConfig.codeChallengeMethod,
            redirectUri = spotifyConfig.redirectUri,
            responseType = spotifyConfig.responseType,
            endpoint = spotifyConfig.spotifyAuthorizeEndpoint
        )
    }

    override suspend fun getToken(code: String, codeVerifier: String): Token {
        val tokenParams = TokenParams(code = code, codeVerifier = codeVerifier)
        val (validCode, validCodeVerifier) = validationService.validateObject(tokenParams)
        return authenticationRepository.getToken(code = validCode, codeVerifier = validCodeVerifier)
    }

    override suspend fun refreshToken(@Valid @NotBlank(message = "the refresh_token field must be filled") refreshToken: String): Token =
        authenticationRepository.refreshToken(refreshToken = refreshToken)
}