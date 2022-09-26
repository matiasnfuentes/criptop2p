package ar.edu.unq.criptop2p

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class Criptop2pApplication

fun main(args: Array<String>) {
    runApplication<Criptop2pApplication>(*args)
}
