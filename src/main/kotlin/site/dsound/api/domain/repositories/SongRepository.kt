package site.dsound.api.domain.repositories

import site.dsound.api.domain.entities.Song

interface SongRepository {
    suspend fun findSongs(): List<Song>
}