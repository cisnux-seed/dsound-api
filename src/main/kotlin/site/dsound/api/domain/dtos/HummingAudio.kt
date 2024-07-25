package site.dsound.api.domain.dtos

import jakarta.validation.constraints.NotNull
import org.springframework.http.codec.multipart.FilePart

data class HummingAudio(
    @field:NotNull(message = "the humming field must be filled")
    val humming: FilePart
)
