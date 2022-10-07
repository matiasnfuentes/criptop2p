package ar.edu.unq.criptop2p.persistance.mockedData

import ar.edu.unq.criptop2p.model.*
import ar.edu.unq.criptop2p.persistance.RequestRepository
import ar.edu.unq.criptop2p.persistance.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class Config {

    @Bean
    fun run(userRepository: UserRepository, requestRepository: RequestRepository): CommandLineRunner {
        return CommandLineRunner { _ ->

            // Setting up users
            val randomPassword = "AsDtest456$"
            val randomStreet = "No name street"
            val randomCity = "No name city"

            val johnDoe = User(
                "John",
                "Doe",
                "john-doe@gmail.com",
                randomPassword,
                "1234567890123456789012",
                "12345678",
                Address(
                    randomStreet, 123, randomCity
                )
            )

            val janePoe = User(
                "Jane",
                "Poe",
                "jane-poe@gmail.com",
                randomPassword,
                "1234567890123456789013",
                "12345679",
                Address(
                    randomStreet, 124, randomCity
                )
            )

            val richardRoe = User(
                "Richard",
                "Roe",
                "richard-roee@gmail.com",
                randomPassword,
                "1234567890123456789014",
                "12345670",
                Address(
                    randomStreet, 125, randomCity
                )
            )

            val users = listOf(johnDoe, janePoe, richardRoe)
            users.forEach { it.encodePassword() }
            userRepository.saveAll(users)

            // Setting up requests

            val currentDate = Date()

            val request1 = Request(
                CryptoCurrency(
                    123.05,
                    "ALICEUSDT",
                    currentDate
                ),
                50.0,
                johnDoe,
                RequestType.BUY
            )

            val request2 = Request(
                CryptoCurrency(
                    145.05,
                    "CAKEUSDT",
                    currentDate
                ),
                140.0,
                janePoe,
                RequestType.SELL
            )

            val request3 = Request(
                CryptoCurrency(
                    200.05,
                    "MATICUSDT",
                    currentDate
                ),
                210.0,
                richardRoe,
                RequestType.BUY
            )
            val request4 = Request(
                CryptoCurrency(
                    145.05,
                    "CAKEUSDT",
                    currentDate
                ),
                15.0,
                richardRoe,
                RequestType.BUY
            )

            requestRepository.saveAll(listOf(request1, request2, request3, request4))
        }
    }
}