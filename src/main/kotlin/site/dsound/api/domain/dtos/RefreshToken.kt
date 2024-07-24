package site.dsound.api.domain.dtos

import jakarta.validation.constraints.NotBlank

data class RefreshToken(
    @field:NotBlank(message = "the refresh_token field must be filled")
    val accessToken: String,
)