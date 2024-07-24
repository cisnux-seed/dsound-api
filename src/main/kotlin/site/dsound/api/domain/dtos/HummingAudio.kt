package site.dsound.api.domain.dtos

import jakarta.validation.constraints.NotNull
import java.io.File

data class HummingAudio(
    @field:NotNull(message = "the humming field must be filled")
    val humming: File
)
