package site.dsound.api.domain.dtos

import jakarta.validation.constraints.NotBlank

data class AccessToken(
    @field:NotBlank(message = "the access_token field must be filled")
    val accessToken: String,
)