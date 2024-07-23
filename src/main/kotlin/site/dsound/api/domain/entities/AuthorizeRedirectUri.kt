package site.dsound.api.domain.entities

data class AuthorizeRedirectUri(
    val clientId: String,
    val endpoint: String,
    val codeVerifier: String,
    val scope: String,
    val responseType: String,
    val codeChallengeMethod: String,
    val codeChallenge: String,
    val redirectUri: String
)