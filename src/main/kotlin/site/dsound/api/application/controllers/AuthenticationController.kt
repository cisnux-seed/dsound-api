package site.dsound.api.application.controllers

import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import site.dsound.api.application.dtos.MetaResponse
import site.dsound.api.application.dtos.WebResponse
import site.dsound.api.domain.entities.AuthorizeRedirectUri
import site.dsound.api.domain.services.AuthenticationService

@RestController
@RequiredArgsConstructor
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
}