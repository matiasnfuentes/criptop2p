package ar.edu.unq.criptop2p.model

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*
import javax.persistence.*

@Entity
class CryptoCurrency(
    @Column
    private val price: Double,
    @field:Schema(name="symbol", description="Symbol must be supported by the platform")
    @Column
    private val symbol: String,
    @Column
    private val timestamp: Date? = Date()
) {
    @Id
    @SequenceGenerator(
        name = "cryptoCurrency_sequence",
        sequenceName = "cryptoCurrency_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "cryptoCurrency_sequence"
    )
    @Column
    private val id: Long? = null

    fun getPrice(): Double = this.price
    fun getSymbol(): String = this.symbol
    fun getTimestamp(): Date? = this.timestamp

    companion object {

        val symbolList = listOf(
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

        fun isAValidCryptoSymbol(symbol: String): Boolean = symbolList.contains(symbol)
    }
}