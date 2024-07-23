package site.dsound.api.domain.services

import org.springframework.stereotype.Service
import site.dsound.api.domain.entities.Song
import java.io.File

@Service
class SongServiceImpl : SongService {
    override suspend fun getSongRecommendation(): List<Song> {
        TODO("Not yet implemented")
    }

    override suspend fun detectSong(humming: File): Song {
        TODO("Not yet implemented")
    }
}