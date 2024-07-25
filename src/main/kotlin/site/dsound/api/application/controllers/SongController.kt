package site.dsound.api.application.controllers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.Part
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
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
    private val log = LoggerFactory.getLogger(SongController::class.java)

    @GetMapping(path = ["/spotify/national-songs"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSongs(@AuthToken accessToken: String): WebResponse<List<Song>> = WebResponse(
        data = songService.getSongRecommendation(accessToken = accessToken), meta = MetaResponse(
            message = "success", code = HttpStatus.OK.value().toString()
        )
    )

    @PostMapping(
        "/spotify/detect"
    )
    @ResponseStatus(HttpStatus.OK)
    suspend fun detectSongs(
        @AuthToken accessToken: String,
        @RequestPart("humming") humming: FilePart
    ): WebResponse<List<Song>> {
        log.info("running humming $humming")
        return WebResponse(
            data = songService.detectSong(humming = humming, accessToken = accessToken), meta = MetaResponse(
                message = "success", code = HttpStatus.OK.value().toString()
            )
        )
    }
}