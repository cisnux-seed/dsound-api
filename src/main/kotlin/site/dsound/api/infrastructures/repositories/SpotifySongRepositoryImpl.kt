package site.dsound.api.infrastructures.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import site.dsound.api.commons.KEYWORD
import site.dsound.api.commons.TRACKS
import site.dsound.api.domain.repositories.SongRepository
import site.dsound.api.infrastructures.dtos.SpotifyResponse
import site.dsound.api.infrastructures.dtos.SpotifyTrackResponse

@Repository
class SpotifySongRepositoryImpl(@Qualifier("spotifyWebClientApi") private val webClient: WebClient) : SongRepository {
    override suspend fun getSongRecommendations(accessToken: String): SpotifyTrackResponse = withContext(Dispatchers.IO) {
        try {
            webClient.get()
                .uri { uriBuilder ->
                    uriBuilder.path("/search")
                        .queryParam("q", KEYWORD)
                        .queryParam("type", TRACKS)
                        .build()
                }
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .onStatus(HttpStatusCode::isError, ClientResponse::createException)
                .awaitBody<SpotifyResponse>().tracks
        } catch (e: Exception) {
            throw e
        }
    }
}