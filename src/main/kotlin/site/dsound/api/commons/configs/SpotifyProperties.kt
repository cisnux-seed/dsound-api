package site.dsound.api.commons.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SpotifyProperties {
    @Value("\${SPOTIFY_CLIENT_ID}")
    lateinit var clientId: String
    @Value("\${SPOTIFY_AUTHORIZE_URI}")
    lateinit var spotifyAuthorizeEndpoint: String
    @Value("\${SPOTIFY_SCOPE}")
    lateinit var scope: String
    @Value("\${SPOTIFY_RESPONSE_TYPE}")
    lateinit var responseType: String
    @Value("\${SPOTIFY_CODE_CHALLENGE_METHOD}")
    lateinit var codeChallengeMethod: String
    @Value("\${SPOTIFY_REDIRECT_URI}")
    lateinit var redirectUri: String
}