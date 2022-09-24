package ar.edu.unq.criptop2p.service

import ar.edu.unq.criptop2p.model.CryptoCurrency
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import java.util.*

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

    private val oneDay = 86400000

    private val baseURL = "https://api.binance.com/api/v3"

    // Este lo deje como modelo para mas adelante
    fun getPrice(cryptoSymbol: String): CryptoCurrency? {
        return this.restTemplate.getForObject(
            this.baseURL +
                    "/ticker/price?symbols=${cryptoSymbol}",
            CryptoCurrency::class.java
        )
    }

    fun getLast24HsPrices(cryptoSymbol: String): List<CryptoCurrency>? {

        if (!symbolList.contains(cryptoSymbol)) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Please provide a valid symbol"
        )

        val endTime = Date().time
        val startTime = endTime - oneDay

        val requestURL = this.baseURL +
                "/klines?interval=1h&symbol=${cryptoSymbol}&startTime=${startTime}&endTime=${endTime}"

        val last24HsPrices = this.restTemplate.getForObject(
            requestURL,
            Array<Array<Any>>::class.java
        )

        return last24HsPrices?.map { CryptoCurrency((it[1] as String).toDouble(), cryptoSymbol, Date(it[0] as Long)) }
            ?.toList()
    }

    fun getCryptoPrices(): List<CryptoCurrency>? {

        val requestURL = this.baseURL + "/ticker/price?symbols=${symbolList.map { "\"${it}\"" }}".replace(
            " ",
            ""
        )

        return restTemplate.getForObject(
            requestURL, Array<CryptoCurrency>::class.java
        )?.toList()
    }

    fun getSymbolList():List<String>{
        return this.symbolList;
    }

}
