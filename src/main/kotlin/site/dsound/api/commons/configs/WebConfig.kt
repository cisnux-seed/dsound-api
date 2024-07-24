package site.dsound.api.commons.configs

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import site.dsound.api.commons.resolvers.AuthTokenArgumentResolver

@Configuration
@EnableWebFlux
class WebConfig(private val authTokenArgumentResolver: AuthTokenArgumentResolver) : WebFluxConfigurer {

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(authTokenArgumentResolver)
    }
}