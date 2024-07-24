package site.dsound.api.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import site.dsound.api.application.dtos.MetaResponse
import site.dsound.api.application.dtos.WebResponse
import site.dsound.api.domain.entities.AuthorizeRedirectUri
import site.dsound.api.domain.entities.Token
import site.dsound.api.domain.services.AuthenticationService

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService,
) {

    @GetMapping("/user/spotify/authorize")
    @ResponseStatus(HttpStatus.OK)
    suspend fun spotifyAuthorize(): WebResponse<AuthorizeRedirectUri> =
        WebResponse(
            data = authenticationService.authorize(),
            meta = MetaResponse(
                code = "200",
                message = "success"
            )
        )

    @GetMapping("/user/spotify/token")
    suspend fun getSpotifyToken(
        @RequestParam("code") code: String,
        @RequestParam("code_verifier") codeVerifier: String
    ): WebResponse<Token> = WebResponse(
        data = authenticationService.getToken(code = code, codeVerifier = codeVerifier),
        meta = MetaResponse(
            code = "200",
            message = "success"
        )
    )

    @GetMapping("/user/spotify/refresh-token")
    suspend fun refreshToken(@RequestParam("refresh_token") refreshToken: String): WebResponse<Token> = WebResponse(
        data = authenticationService.refreshToken(refreshToken = refreshToken),
        meta = MetaResponse(
            code = "200",
            message = "success"
        )
    )
}