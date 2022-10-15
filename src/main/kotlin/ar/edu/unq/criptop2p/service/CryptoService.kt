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
    private val baseURL = "https://api.binance.com/api/v3"

    fun getPrice(cryptoSymbol: String): Double {
        checkCryptoSymbol(cryptoSymbol)

        val requestURL = this.baseURL + "/ticker/price?symbol=${cryptoSymbol}"

        val cryptoCurrency = this.restTemplate.getForObject(
            requestURL, CryptoCurrency::class.java
        )

        return cryptoCurrency?.getPrice() ?: 0.0
    }

    fun getLast24HsPrices(cryptoSymbol: String): List<CryptoCurrency>? {
        checkCryptoSymbol(cryptoSymbol)

        val oneDay = 86400000
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

        val symbolListParsed = CryptoCurrency.symbolList.map { "\"${it}\"" }.toString().replace(
            " ",
            ""
        )

        val requestURL = this.baseURL + "/ticker/price?symbols=${symbolListParsed}"

        return restTemplate.getForObject(
            requestURL, Array<CryptoCurrency>::class.java
        )?.toList()
    }

    fun checkCryptoSymbol(cryptoSymbol: String) {
        if (!CryptoCurrency.isAValidCryptoSymbol(cryptoSymbol)) throw ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Please provide a valid symbol"
        )
    }

    fun getSymbolList():List<String>{
        return CryptoCurrency.symbolList
    }
}
