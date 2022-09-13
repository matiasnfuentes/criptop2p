package ar.edu.unq.criptop2p.model

import java.time.LocalDate

class CryptoCurrency(private val price: Double, private val symbol: String) {

    private val timestamp: LocalDate = LocalDate.now()

    fun getPrice(): Double = this.price
    fun getSymbol(): String = this.symbol
}