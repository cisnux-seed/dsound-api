package site.dsound.api.domain.dtos

import jakarta.validation.constraints.NotBlank

data class TokenParams(
    @NotBlank(message = "the code field must be filled")
    val code: String,
    @NotBlank(message = "the code_verifier field must be filled")
    val codeVerifier: String,
)