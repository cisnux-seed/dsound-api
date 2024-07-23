package site.dsound.api.commons.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Value("\${SPOTIFY_AUTHENTICATION_URL}")
    lateinit var spotifyAuthBaseUrl: String

    @Value("\${SPOTIFY_API_URL}")
    lateinit var spotifyApiBaseUrl: String

    @Bean("spotifyWebClientAuth")
    fun spotifyClientAuth(): WebClient {
        return WebClient.builder().baseUrl(spotifyAuthBaseUrl).build()
    }

    @Bean("spotifyWebClientApi")
    fun spotifyClientApi(): WebClient {
        return WebClient.builder().baseUrl(spotifyApiBaseUrl).build()
    }
}