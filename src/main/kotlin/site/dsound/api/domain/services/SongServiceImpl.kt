package site.dsound.api.domain.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import site.dsound.api.domain.entities.Song
import site.dsound.api.domain.repositories.SongRepository
import java.io.File

@Service
class SongServiceImpl(private val songRepository: SongRepository) : SongService {
    override suspend fun getSongRecommendation(accessToken: String): List<Song> = withContext(Dispatchers.IO) {
        songRepository.getSongRecommendations(accessToken = accessToken).items.map { track ->
            Song(
                id = track.id,
                title = track.name,
                imageUrl = track.album.images.firstOrNull()?.url,
                trackUri = track.uri,
                artistName = track.artists.firstOrNull()?.name ?: "Undefined",
            )
        }
    }

    override suspend fun detectSong(humming: File): Song {
        TODO("Not yet implemented")
    }
}