package site.dsound.api.commons.configs

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KtorConfig(private val acrProperties: AcrProperties) {
    @OptIn(ExperimentalSerializationApi::class)
    @Bean("ktorAcrClientApi")
    fun acrClientApi(): HttpClient = HttpClient(CIO) {
        defaultRequest {
            url(acrProperties.url)
        }
        install(HttpTimeout){
            requestTimeoutMillis = 15_000L
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }
    }
}