package ar.edu.unq.criptop2p.model

import java.util.*
import javax.persistence.*

@Entity
class CryptoCurrency(@Column
                     private val price: Double,
                     @Column
                     private val symbol: String) {

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
    @Column
    private val timestamp: Date = Date()


    fun getPrice(): Double = this.price
    fun getSymbol(): String = this.symbol
}