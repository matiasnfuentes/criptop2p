package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.CryptoCurrency
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CryptoService {

    private val restTemplate = RestTemplate()

    private val symbolList = listOf(
        "ALICEUSDT",
        "MATICUSDT",
        "AXSUSDT",
        "AAVEUSDT",
        "ATOMUSDT",
        "NEOUSDT",
        "DOTUSDT",
        "ETHUSDT",
        "CAKEUSDT",
        "BTCUSDT",
        "BNBUSDT",
        "ADAUSDT",
        "TRXUSDT",
        "AUDIOUSDT"
    )

    private val baseURL = "https://api.binance.com/api/v3"

    // Este lo deje como modelo para mas adelante
    fun getPrice(cryptoSymbol: String): CryptoCurrency? {
        return this.restTemplate.getForObject(
            this.baseURL +
                    "/ticker/price?symbols=${cryptoSymbol}",
            CryptoCurrency::class.java
        )
    }

    fun getCryptoPrices(): List<CryptoCurrency>? {
        return restTemplate.getForObject(
            this.baseURL + "/ticker/price?symbols=${symbolList.map { "\"${it}\"" }}".replace(
                " ",
                ""
            ), Array<CryptoCurrency>::class.java
        )?.toList()
    }

}
