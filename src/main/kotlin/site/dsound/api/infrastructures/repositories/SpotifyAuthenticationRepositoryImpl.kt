package site.dsound.api.infrastructures.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Repository
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import site.dsound.api.commons.configs.SpotifyProperties
import site.dsound.api.domain.entities.Token
import site.dsound.api.domain.repositories.AuthenticationRepository

@Repository
class SpotifyAuthenticationRepositoryImpl(
    @Qualifier("spotifyWebClientAuth") private val webClient: WebClient,
    private val spotifyProperties: SpotifyProperties,
) :
    AuthenticationRepository {
    override suspend fun getToken(code: String, codeVerifier: String): Token = withContext(Dispatchers.IO) {
        val formData = LinkedMultiValueMap<String, String>()
        formData["client_id"] = spotifyProperties.clientId
        formData["grant_type"] = "authorization_code"
        formData["code"] = code
        formData["redirect_uri"] = spotifyProperties.redirectUri
        formData["code_verifier"] = codeVerifier

        webClient.post()
            .bodyValue(formData)
            .retrieve()
            .onStatus(HttpStatusCode::isError, ClientResponse::createException)
            .awaitBody<Token>()
    }

    override suspend fun refreshToken(refreshToken: String): Token = withContext(Dispatchers.IO) {
        val formData = LinkedMultiValueMap<String, String>()
        formData["client_id"] = spotifyProperties.clientId
        formData["grant_type"] = "refresh_token"
        formData["refresh_token"] = refreshToken

        webClient.post()
            .bodyValue(formData)
            .retrieve()
            .onStatus(HttpStatusCode::isError, ClientResponse::createException)
            .awaitBody<Token>()
    }
}