package site.dsound.api.domain.dtos

import jakarta.validation.constraints.NotBlank

data class RefreshTokenParams(
    @field:NotBlank(message = "the refresh_token field must be filled")
    val refreshToken: String,
)