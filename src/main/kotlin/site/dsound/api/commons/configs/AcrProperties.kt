package site.dsound.api.commons.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class AcrProperties {
    @Value("\${ACR_ACCESS_KEY}")
    lateinit var accessKey: String
    @Value("\${ACR_SECRET_KEY}")
    lateinit var accessSecret: String
    @Value("\${ACR_REQ_URL}")
    lateinit var url: String
}