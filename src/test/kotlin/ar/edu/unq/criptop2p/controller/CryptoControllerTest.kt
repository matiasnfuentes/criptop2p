package ar.edu.unq.criptop2p.controller

import ar.edu.unq.criptop2p.model.CryptoCurrency
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.client.RestTemplate
import java.util.*
import kotlin.test.assertEquals


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(profiles = ["test"])
class CryptoControllerTest(
    @Autowired
    val mockMvc: MockMvc
) {

    @MockkBean
    lateinit var restTemplate: RestTemplate

    @Test
    fun last24HsPrices() {
        val lastPrices: Array<Array<Any>> = arrayOf(
            arrayOf(1666310400000, "19041.92000000"),
            arrayOf(1666314000000, "19048.83000000"),
            arrayOf(1666317600000, "19065.86000000"),
            arrayOf(1666321200000, "19109.49000000")
        )

        val expectedResponse =
            "[{\"price\":19041.92,\"symbol\":\"BTCUSDT\",\"timeStamp\":\"2022-10-21T00:00:00.000+00:00\"}," +
             "{\"price\":19048.83,\"symbol\":\"BTCUSDT\",\"timeStamp\":\"2022-10-21T01:00:00.000+00:00\"}," +
             "{\"price\":19065.86,\"symbol\":\"BTCUSDT\",\"timeStamp\":\"2022-10-21T02:00:00.000+00:00\"}," +
             "{\"price\":19109.49,\"symbol\":\"BTCUSDT\",\"timeStamp\":\"2022-10-21T03:00:00.000+00:00\"}]"

        every {
            restTemplate.getForObject(
                any<String>(),
                Array<Array<Any>>::class.java
            )
        } returns lastPrices

        val mvcResponse = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/crypto/price/BTCUSDT")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals(mvcResponse.response.contentAsString, expectedResponse)
    }

    @Test
    fun prices() {
        val date = Date(1666580400000)
        val lastPrices: Array<CryptoCurrency> = arrayOf(
            CryptoCurrency(19041.92000000, "BTCUSDT", date),
            CryptoCurrency(12041.92000000, "MATICUSDT", date),
            CryptoCurrency(15041.92000000, "TRXUSDT", date),
        )

        val expectedResponse =
            "[{\"price\":19041.92,\"symbol\":\"BTCUSDT\",\"timeStamp\":\"2022-10-24T03:00:00.000+00:00\"}," +
             "{\"price\":12041.92,\"symbol\":\"MATICUSDT\",\"timeStamp\":\"2022-10-24T03:00:00.000+00:00\"}," +
             "{\"price\":15041.92,\"symbol\":\"TRXUSDT\",\"timeStamp\":\"2022-10-24T03:00:00.000+00:00\"}]"

        every {
            restTemplate.getForObject(
                any<String>(),
                Array<CryptoCurrency>::class.java
            )
        } returns lastPrices

        val mvcResponse = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/crypto/prices")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals(mvcResponse.response.contentAsString, expectedResponse)
    }

}