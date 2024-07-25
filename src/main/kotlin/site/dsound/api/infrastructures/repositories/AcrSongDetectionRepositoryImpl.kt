package site.dsound.api.infrastructures.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import site.dsound.api.commons.configs.AcrProperties
import site.dsound.api.domain.repositories.SongDetectionRepository
import site.dsound.api.infrastructures.dtos.AcrCloudHummingResponse
import site.dsound.api.infrastructures.dtos.AcrCloudResponse
import java.io.ByteArrayOutputStream

@Repository
class AcrSongDetectionRepositoryImpl(
    @Qualifier("acrClientApi") private val webClient: WebClient,
    private val acrProperties: AcrProperties,
) : SongDetectionRepository {
    override suspend fun detectSong(humming: FilePart): List<AcrCloudHummingResponse> = withContext(Dispatchers.IO) {
        val byteArray = humming.content().collectList().awaitSingleOrNull()?.let { dataBuffers ->
            val outputStream = ByteArrayOutputStream()
            dataBuffers.forEach { dataBuffer ->
                val inputStream = dataBuffer.asInputStream()
                inputStream.copyTo(outputStream)
            }
            outputStream.toByteArray()
        } ?: ByteArray(0)

        val bodyBuilder = MultipartBodyBuilder().apply {
            part("file", ByteArrayResource(byteArray))
                .filename(humming.filename())
        }

        try {
            webClient.post()
                .uri("http://localhost:9090/upload")
                .bodyValue(bodyBuilder.build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ClientResponse::createException)
                .awaitBody<AcrCloudResponse>().metadata.humming
        } catch (e: Exception) {
            throw e
        }
    }
}