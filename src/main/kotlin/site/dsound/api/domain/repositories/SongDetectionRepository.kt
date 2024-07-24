package site.dsound.api.domain.repositories

import site.dsound.api.infrastructures.dtos.AcrCloudHummingResponse
import java.io.File

interface SongDetectionRepository {
    suspend fun detectSong(humming: File): List<AcrCloudHummingResponse>
}