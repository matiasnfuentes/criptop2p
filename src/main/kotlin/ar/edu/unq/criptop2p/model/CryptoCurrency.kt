package ar.edu.unq.criptop2p.model

import java.util.*

class CryptoCurrency(private val price: Double, private val symbol: String, private val date: Date = Date() ) {

    fun getPrice(): Double = this.price
    fun getSymbol(): String = this.symbol
    fun getDate(): Date = this.date
}