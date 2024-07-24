package site.dsound.api.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import site.dsound.api.application.dtos.MetaResponse
import site.dsound.api.application.dtos.WebResponse
import site.dsound.api.commons.resolvers.AuthToken
import site.dsound.api.domain.entities.Song
import site.dsound.api.domain.services.SongService
import java.io.File

@RestController
class SongController(
    private val songService: SongService
) {
    @GetMapping(path = ["/spotify/national-songs"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSongs(@AuthToken accessToken: String): WebResponse<List<Song>> = WebResponse(
        data = songService.getSongRecommendation(accessToken = accessToken), meta = MetaResponse(
            message = "success", code = HttpStatus.OK.value().toString()
        )
    )

    @PostMapping(
        "/spotify/detect",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSongs(
        @AuthToken accessToken: String,
        @RequestPart("humming") humming: File
    ): WebResponse<List<Song>> = WebResponse(
        data = songService.detectSong(accessToken = accessToken, humming = humming), meta = MetaResponse(
            message = "success", code = HttpStatus.OK.value().toString()
        )
    )
}