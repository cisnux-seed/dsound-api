package site.dsound.api.domain.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import site.dsound.api.domain.dtos.AccessToken
import site.dsound.api.domain.dtos.HummingAudio
import site.dsound.api.domain.entities.Song
import site.dsound.api.domain.repositories.SongDetectionRepository
import site.dsound.api.domain.repositories.SongRepository
import java.io.File

@Service
class SongServiceImpl(
    private val songRepository: SongRepository, private val songDetectionRepository: SongDetectionRepository,
    private val validationService: ValidationService,
) : SongService {
    override suspend fun getSongRecommendation(accessToken: String): List<Song> = withContext(Dispatchers.Default) {
        val (validAccessToken) = validationService.validateObject(AccessToken(accessToken))

        songRepository.getSongRecommendations(accessToken = validAccessToken).items.map { track ->
            Song(
                id = track.id,
                title = track.name,
                imageUrl = track.album.images.firstOrNull()?.url,
                trackUri = track.uri,
                artistName = track.artists.firstOrNull()?.name ?: "Undefined",
            )
        }
    }

    override suspend fun detectSong(humming: File, accessToken: String): List<Song> =
        withContext(Dispatchers.Default) {
            val (validAccessToken) = validationService.validateObject(AccessToken(accessToken))
            val (validHummingAudio) = validationService.validateObject(HummingAudio(humming))

            val acrHummings = songDetectionRepository.detectSong(humming = validHummingAudio)
            acrHummings.map { acrHumming ->
                val trackItemResponse = songRepository.findSongs(accessToken = validAccessToken,
                    query = "${
                        acrHumming.artists.firstOrNull()?.let { "${it.name} " } ?: ""
                    }${acrHumming.title}")
                    .items
                    .firstOrNull()

                trackItemResponse?.let { track ->
                    Song(
                        id = track.id,
                        title = track.name,
                        imageUrl = track.album.images.firstOrNull()?.url,
                        trackUri = track.uri,
                        artistName = track.artists.firstOrNull()?.name ?: "Undefined",
                        score = acrHumming.score
                    )
                }
            }
        }
            .filterNotNull()
}