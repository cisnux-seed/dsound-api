package site.dsound.api.domain.repositories

import site.dsound.api.infrastructures.dtos.SpotifyTrackResponse

interface SongRepository {
    suspend fun getSongRecommendations(accessToken: String): SpotifyTrackResponse
}