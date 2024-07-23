package site.dsound.api.infrastructures.repositories

import org.springframework.stereotype.Repository
import site.dsound.api.domain.entities.Song
import site.dsound.api.domain.repositories.SongRepository

@Repository
class SpotifySongRepositoryImpl : SongRepository {
    override suspend fun findSongs(): List<Song> = listOf()
}