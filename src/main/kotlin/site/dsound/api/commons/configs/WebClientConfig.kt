package site.dsound.api.commons.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(private val spotifyProperties: SpotifyProperties, private val acrProperties: AcrProperties) {
    @Bean("spotifyWebClientAuth")
    fun spotifyClientAuth(): WebClient {
        return WebClient.builder().baseUrl(spotifyProperties.spotifyAuthBaseUrl).build()
    }

    @Bean("spotifyWebClientApi")
    fun spotifyClientApi(): WebClient {
        return WebClient.builder().baseUrl(spotifyProperties.spotifyApiBaseUrl).build()
    }

    @Bean("acrClientApi")
    fun acrClientApi(): WebClient {
        return WebClient.builder().build()
    }
}