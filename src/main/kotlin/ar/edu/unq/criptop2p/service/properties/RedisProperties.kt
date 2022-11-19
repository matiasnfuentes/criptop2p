package ar.edu.unq.criptop2p.service.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("redis")
class RedisProperties {
    lateinit var host: String
    lateinit var auth: String
    var port: Int = 0
}