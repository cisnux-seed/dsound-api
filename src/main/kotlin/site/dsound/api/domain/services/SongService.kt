package site.dsound.api.domain.services

import site.dsound.api.domain.entities.Song
import java.io.File

interface SongService {
    suspend fun getSongRecommendation(): List<Song>
    suspend fun detectSong(humming: File): Song
}