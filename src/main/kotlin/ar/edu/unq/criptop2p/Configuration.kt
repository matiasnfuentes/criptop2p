package ar.edu.unq.criptop2p


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestTemplate


@Profile(value = ["dev", "test"])
@Configuration
class Configuration {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

}