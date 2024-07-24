package site.dsound.api.application.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import site.dsound.api.application.dtos.MetaResponse
import site.dsound.api.application.dtos.WebResponse
import site.dsound.api.commons.resolvers.AuthToken
import site.dsound.api.domain.entities.Song
import site.dsound.api.domain.services.SongService

@RestController
class SongController(
    private val songService: SongService
) {
    @GetMapping("/spotify/national-songs")
    @ResponseStatus(HttpStatus.OK)
    suspend fun getSongs(@AuthToken accessToken: String): WebResponse<List<Song>> = WebResponse(
        data = songService.getSongRecommendation(accessToken = accessToken), meta = MetaResponse(
            message = "success", code = HttpStatus.OK.value().toString()
        )
    )
}