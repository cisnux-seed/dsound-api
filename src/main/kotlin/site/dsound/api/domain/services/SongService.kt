package site.dsound.api.domain.services

import org.springframework.http.codec.multipart.FilePart
import site.dsound.api.domain.entities.Song

interface SongService {
    suspend fun getSongRecommendation(accessToken: String): List<Song>
    suspend fun detectSong(humming: FilePart, accessToken: String): List<Song>
}