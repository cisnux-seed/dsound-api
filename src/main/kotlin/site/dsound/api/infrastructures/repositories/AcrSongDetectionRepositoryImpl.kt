package site.dsound.api.infrastructures.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import site.dsound.api.commons.configs.AcrProperties
import site.dsound.api.domain.repositories.SongDetectionRepository
import site.dsound.api.infrastructures.dtos.AcrCloudHummingResponse
import site.dsound.api.infrastructures.dtos.AcrCloudResponse
import java.io.File
import java.nio.file.Files
import java.time.Instant
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Repository
class AcrSongDetectionRepositoryImpl(
    @Qualifier("acrClientApi") private val webClient: WebClient,
    private val acrProperties: AcrProperties
) : SongDetectionRepository {
    override suspend fun detectSong(humming: File): List<AcrCloudHummingResponse> = withContext(Dispatchers.IO) {
        val httpMethod = "POST"
        val httpUri = "/v1/identify"
        val dataType = "audio"
        val signatureVersion = "1"
        val timestamp = Instant.now().epochSecond.toString()

        val stringToSign =
            "$httpMethod\n$httpUri\n$${acrProperties.accessKey}\n$dataType\n$signatureVersion\n$timestamp"
        val secretKeySpec = SecretKeySpec(acrProperties.accessSecret.toByteArray(), "HmacSHA1")
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(secretKeySpec)
        val signatureBytes = mac.doFinal(stringToSign.toByteArray())
        val signature = Base64.getEncoder().encodeToString(signatureBytes)

        val sampleBytes = Files.size(humming.toPath()).toString()

        val bodyBuilder = MultipartBodyBuilder().apply {
            part("access_key", acrProperties.accessKey)
            part("sample_bytes", sampleBytes)
            part("timestamp", timestamp)
            part("signature", signature)
            part("data_type", dataType)
            part("signature_version", signatureVersion)
            part("sample", ByteArrayResource(Files.readAllBytes(humming.toPath()))).filename(humming.name)
        }


        try {
            val response = webClient.post()
                .bodyValue(bodyBuilder.build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, ClientResponse::createException)
                .awaitBody<AcrCloudResponse>()

            response.metadata.humming
        } catch (e: Exception) {
            throw e
        }
    }
}
