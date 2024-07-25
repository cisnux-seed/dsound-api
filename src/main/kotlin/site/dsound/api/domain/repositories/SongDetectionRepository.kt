package site.dsound.api.domain.repositories

import org.springframework.http.codec.multipart.FilePart
import site.dsound.api.infrastructures.dtos.AcrCloudHummingResponse
import java.io.File

interface SongDetectionRepository {
    suspend fun detectSong(humming: FilePart): List<AcrCloudHummingResponse>
}